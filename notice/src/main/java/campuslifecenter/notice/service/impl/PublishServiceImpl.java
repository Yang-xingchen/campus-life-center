package campuslifecenter.notice.service.impl;

import campuslifecenter.common.component.TracerUtil;
import campuslifecenter.common.exception.ProcessException;
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
            tracerUtil.newSpanAsyn("update todo", scopedSpan -> {
                try {
                    todoService.updateAccount(aids, notice.getRef());
                } finally {
                    refCountDown.countDown();
                }
            });
            infoRefs.forEach(ref -> tracerUtil.newSpanAsyn("update info: " + ref, scopedSpan -> {
                try {
                    informationService.updateAccount(ref, aids);
                } finally {
                    refCountDown.countDown();
                }
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
            List<PermissionService.Permission> permissions = permissionService
                    .getPermission(notice.getCreator(), notice.getOrganization())
                    .checkGet(USER_CENTER, "get permission fail");
            int max = permissions.stream()
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
        // 待办信息
        tracerUtil.newSpan("insert todo", scopedSpan -> {
            Response<Boolean> response = todoService.add(new TodoService.AddTodoRequest()
                    .setRef(notice.getRef())
                    .setAids(aids)
                    .setValues(publishNotice.getTodo()));
            if (!response.checkGet(TODO, "insert todo fail")) {
                throw new ProcessException(TODO, "insert todo fail", response);
            }
        });
        // 标签
        tracerUtil.newSpan("insert tag", scopedSpan -> {
            tagService.addTag(publishNotice.getTag(), notice.getId());
        });
        // 信息收集
        tracerUtil.newSpan("insert info collect", scopedSpan -> {
            publishNotice
                    .getInfoCollects()
                    .stream()
                    .peek(addInfoRequest -> addInfoRequest.setAids(aids))
                    .forEach(infoCollect -> {
                        Response<String> response = informationService.addInfoCollect(infoCollect);
                        NoticeInfoKey noticeInfoKey = new NoticeInfoKey();
                        noticeInfoKey.withNid(notice.getId()).withRef(response.checkGet(INFO, "add info fail"));
                        infoMapper.insert(noticeInfoKey);
                    });
        });
        // 注册通知条件
        tracerUtil.newSpan("insert dynamic", scopedSpan -> {
            scopedSpan.annotate("account");
            publishNotice.getAccountList()
                    .stream()
                    .map(aid -> new PublishAccountKey().withAid(aid).withId(notice.getId()))
                    .forEach(publishAccountMapper::insert);
            // 待办
            scopedSpan.annotate("todo");
            publishNotice
                    .getTodoList()
                    .stream()
                    .peek(todo -> todo.setNid(notice.getId()))
                    .forEach(publishTodoMapper::insertSelective);
            // 信息
            scopedSpan.annotate("info");
            publishNotice
                    .getInfoList()
                    .stream()
                    .peek(info -> info.setNid(notice.getId()))
                    .forEach(publishInfoMapper::insertSelective);
            // 组织
            scopedSpan.annotate("organization");
            publishNotice
                    .getOrganizationList()
                    .stream()
                    .peek(info -> info.setNid(notice.getId()))
                    .forEach(publishOrganizationMapper::insertSelective);
        });
        if (notice.getPublishStatus() == STATUS_PUBLISHING) {
            messageChannel.send(MessageBuilder.withPayload(notice.getId()).build());
        }
        return notice.getId();
    }

}
