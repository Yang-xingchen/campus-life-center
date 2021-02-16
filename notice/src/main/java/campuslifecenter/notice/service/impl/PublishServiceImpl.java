package campuslifecenter.notice.service.impl;

import campuslifecenter.common.component.TracerUtil;
import campuslifecenter.common.exception.ResponseException;
import campuslifecenter.common.model.Response;
import campuslifecenter.notice.component.NoticeStream;
import campuslifecenter.notice.entry.*;
import campuslifecenter.notice.mapper.*;
import campuslifecenter.notice.model.*;
import campuslifecenter.notice.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static campuslifecenter.common.exception.ProcessException.*;
import static campuslifecenter.notice.model.NoticeConst.*;
import static java.util.stream.Collectors.toList;

@Service
public class PublishServiceImpl implements PublishService {

    @Autowired
    private NoticeMapper noticeMapper;
    @Autowired
    private NoticeInfoMapper infoMapper;
    @Autowired
    private AccountNoticeMapper accountNoticeMapper;

    @Autowired
    private PublishAccountMapper publishAccountMapper;
    @Autowired
    private PublishTodoMapper publishTodoMapper;
    @Autowired
    private PublishInfoMapper publishInfoMapper;
    @Autowired
    private PublishOrganizationMapper publishOrganizationMapper;

    @Autowired
    @Qualifier(NoticeStream.PUBLISH_ACCOUNT)
    private MessageChannel messageChannel;

    @Autowired
    private PublishAccountService publishAccountService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private TagService tagService;
    @Autowired
    private TodoService todoService;
    @Autowired
    private InformationService informationService;
    @Autowired
    private CacheService cacheService;

    @Value("${notice.redis.publish-notice}")
    private String PUBLISH_PREFIX;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private TracerUtil tracerUtil;

    @Override
    public String getPublishId(String token) {
        String aid = cacheService.getAccountIdByToken(token);
        String pid = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(PUBLISH_PREFIX + pid, aid, 1, TimeUnit.DAYS);
        return pid;
    }

    @Override
    @NewSpan("get publish creator id")
    public String getPublishAid(String pid) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(PUBLISH_PREFIX + pid)).orElseGet(() -> {
            NoticeExample example = new NoticeExample();
            example.createCriteria().andRefEqualTo(pid);
            return noticeMapper.selectByExample(example).get(0).getCreator();
        });
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean publishNoticeAccount(Notice notice, List<String> aids) {
        long nid = notice.getId();
        tracerUtil.newSpan("insert account notice", scopedSpan -> {
            aids.stream()
                    .map(accountId -> (AccountNotice) new AccountNotice().withAid(accountId).withNid(notice.getId()))
                    .filter(accountNotice -> accountNoticeMapper.selectByPrimaryKey(accountNotice) == null)
                    .forEach(accountNoticeMapper::insertSelective);
        });
        if (notice.getPublishStatus() == STATUS_WAIT) {
            List<String> infoRefs = tracerUtil.newSpan("get info ref", scopedSpan -> {
                NoticeInfoExample example = new NoticeInfoExample();
                example.createCriteria().andNidEqualTo(nid);
                return infoMapper.selectByExample(example)
                        .stream()
                        .map(NoticeInfoKey::getRef)
                        .collect(toList());
            });
            CountDownLatch refCountDown = new CountDownLatch(infoRefs.size() + 1);
            tracerUtil.newSpanAsync("update todo", refCountDown, scopedSpan -> {
                todoService.updateAccount(aids, notice.getRef());
            });
            infoRefs.forEach(ref -> tracerUtil.newSpanAsync("update info: " + ref, refCountDown, scopedSpan -> {
                informationService.updateAccount(ref, aids);
            }));
            try {
                refCountDown.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        tracerUtil.newSpan("update status", scopedSpan -> {
            Notice notice1 = new Notice()
                    .withId(nid)
                    .withPublishStatus(NoticeConst.STATUS_PUBLISHED);
            noticeMapper.updateByPrimaryKeySelective(notice1);
        });
        return true;
    }

    @Override
    @NewSpan("publish notice")
    @Transactional(rollbackFor = RuntimeException.class)
    public Long publishNotice(PublishNotice publishNotice) {
        Notice notice = publishNotice.getNotice();
        if (!Objects.equals(redisTemplate.delete(PUBLISH_PREFIX + publishNotice.getPid()), true)) {
            throw new ResponseException("already publish");
        }
        tracerUtil.newSpan("check permission", scopedSpan -> {
            if (ORGANIZATION_SELF == notice.getOrganization()) {
                notice.setPublishStatus(STATUS_PUBLISHING);
                return;
            }
            int max = getMaxImportance(notice.getCreator(), notice.getOrganization());
            notice.setPublishStatus(max >= notice.getImportance() ? STATUS_PUBLISHING : STATUS_WAIT);
        });
        // 通知
        tracerUtil.newSpan("init", scopedSpan -> {
            notice.setVersion(1);
            notice.setCreateTime(new Date());
            notice.setRef(publishNotice.getPid());
        });
        tracerUtil.newSpanNRet("insert notice", scopedSpan -> noticeMapper.insertSelective(notice));
        // 成员列表
        List<String> aids = tracerUtil.newSpan("get publish account list", scopedSpan -> {
            // 如果需要等待审核，则不发布到成员
            if (notice.getPublishStatus() == STATUS_WAIT) {
                return List.of();
            } else {
                return publishAccountService
                        .publishAccountsStream(publishNotice, false)
                        .map(PublishAccounts::getAccounts)
                        .flatMap(List::stream)
                        .map(IdName::getId)
                        .distinct()
                        .collect(toList());
            }
        });
        // 引用
        CountDownLatch countDownLatch = tracerUtil.newSpan("insert ref", scopedSpan -> {
            List<InformationService.AddInfoRequest> addInfoRequests = publishNotice
                    .getInfoCollects()
                    .stream()
                    .peek(addInfoRequest -> addInfoRequest.setAids(aids))
                    .collect(toList());
            TodoService.AddTodoRequest addTodoRequest = new TodoService.AddTodoRequest()
                    .setRef(notice.getRef())
                    .setAids(aids)
                    .setValues(publishNotice.getTodo());
            CountDownLatch refCountDownLatch = new CountDownLatch(addInfoRequests.size() + 1);
            // 待办
            tracerUtil.newSpanAsync("insert todo", refCountDownLatch,
                    scopedSpan1 -> todoService.add(addTodoRequest).checkGet(TODO, "insert todo fail"));
            // 信息收集
            addInfoRequests.forEach(addInfoRequest -> tracerUtil.newSpanAsync("insert info: " + addInfoRequest.getName(), refCountDownLatch, scopedSpan1 -> {
                Response<String> response = informationService.addInfoCollect(addInfoRequest);
                NoticeInfoKey noticeInfoKey = new NoticeInfoKey();
                noticeInfoKey.withNid(notice.getId()).withRef(response.checkGet(INFO, "add info fail"));
                infoMapper.insert(noticeInfoKey);
            }));
            return refCountDownLatch;
        });
        // 标签
        tracerUtil.newSpan("insert tag", scopedSpan -> {
            tagService.addTag(publishNotice.getTag(), notice.getId());
        });
        // 注册通知条件
        insertCondition(publishNotice);
        // 等待外部系统
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // 是否发布
        if (notice.getPublishStatus() == STATUS_PUBLISHING) {
            messageChannel.send(MessageBuilder.withPayload(notice.getId()).build());
        }
        return notice.getId();
    }

    private void insertCondition(PublishNotice publishNotice) {
        long id = publishNotice.getNotice().getId();
        tracerUtil.newSpan("insert dynamic", scopedSpan -> {
            // 独立
            scopedSpan.annotate("account");
            publishNotice.getAccountList()
                    .stream()
                    .map(aid -> new PublishAccountKey().withAid(aid).withId(id))
                    .forEach(publishAccountMapper::insert);
            // 待办
            scopedSpan.annotate("todo");
            publishNotice
                    .getTodoList()
                    .stream()
                    .peek(todo -> todo.setNid(id))
                    .forEach(publishTodoMapper::insertSelective);
            // 信息
            scopedSpan.annotate("info");
            publishNotice
                    .getInfoList()
                    .stream()
                    .peek(info -> info.setNid(id))
                    .forEach(publishInfoMapper::insertSelective);
            // 组织
            scopedSpan.annotate("organization");
            publishNotice
                    .getOrganizationList()
                    .stream()
                    .peek(info -> info.setNid(id))
                    .forEach(publishOrganizationMapper::insertSelective);
        });
    }

    private int getMaxImportance(String aid, int organization) {
        List<PermissionService.Permission> permissions = permissionService
                .getPermission(aid, organization)
                .checkGet(USER_CENTER, "get permission fail");
        return permissions.stream()
                .filter(permission -> permission.getName().startsWith("importance"))
                .mapToInt(permission -> {
                    try {
                        return Integer.parseInt(permission.getName().split(":")[1]);
                    } catch (RuntimeException e) {
                        e.printStackTrace();
                        return -1;
                    }
                })
                .max()
                .orElse(-1);
    }

}
