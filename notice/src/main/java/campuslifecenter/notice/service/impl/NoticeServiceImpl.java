package campuslifecenter.notice.service.impl;

import campuslifecenter.common.component.TracerUtil;
import campuslifecenter.notice.component.NoticeStream;
import campuslifecenter.notice.entry.*;
import campuslifecenter.notice.mapper.*;
import campuslifecenter.notice.model.AccountNoticeInfo;
import campuslifecenter.notice.model.NoticeConst;
import campuslifecenter.notice.model.NoticeInfo;
import campuslifecenter.notice.service.CacheService;
import campuslifecenter.notice.service.NoticeService;
import campuslifecenter.notice.service.TagService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.cloud.sleuth.annotation.SpanTag;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;
    @Autowired
    private NoticeInfoMapper infoMapper;
    @Autowired
    private AccountNoticeMapper accountNoticeMapper;
    @Autowired
    private NoticeTagMapper noticeTagMapper;

    @Autowired
    private NoticeUpdateLogMapper updateMapper;

    @Autowired
    @Qualifier(NoticeStream.PUBLISH_NOTICE)
    private MessageChannel publishNoticeChannel;

    @Autowired
    private TagService tagService;
    @Autowired
    private CacheService cacheService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private TracerUtil tracerUtil;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Value("${notice.redis.cache.notice}")
    private String NOTICE_PREFIX;
    @Value("${notice.save-path}")
    private String SAVE_PATH_PREFIX;
    @Value("${notice.uri-path}")
    private String URI_PATH_PREFIX;

    @Override
    @NewSpan("get all notice operation")
    public List<AccountNoticeInfo> getAllNoticeOperationByAid(@SpanTag("account id") String aid) {
        AccountNoticeExample noticeExample = new AccountNoticeExample();
        noticeExample.createCriteria().andAidEqualTo(aid);
        return accountNoticeMapper
                .selectByExample(noticeExample)
                .stream()
                .map(AccountNoticeInfo::createByAccountNotice)
                .collect(Collectors.toList());
    }

    @Override
    @NewSpan("get notice info")
    public AccountNoticeInfo getNoticeById(@SpanTag("id") long nid) {
        BoundValueOperations<String, String> noticeOps = redisTemplate.boundValueOps(NOTICE_PREFIX + nid);
        String cache = noticeOps.get();
        if (cache != null) {
            try {
                return objectMapper.readValue(cache, AccountNoticeInfo.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        AccountNoticeInfo accountNoticeInfo = tracerUtil.newSpan("notice", span -> {
            Notice notice = noticeMapper.selectByPrimaryKey(nid);
            if (notice == null) {
                return null;
            }
            return AccountNoticeInfo.createByNotice(notice);
        });
        if (accountNoticeInfo == null) {
            return null;
        }
        tracerUtil.newSpan("tag", span -> {
            NoticeTagExample tagExample = new NoticeTagExample();
            tagExample.createCriteria().andNidEqualTo(nid);
            accountNoticeInfo.withNoticeTag(noticeTagMapper.selectByExample(tagExample));
        });
        tracerUtil.newSpan("file", span -> {
            String fileRef = accountNoticeInfo.getRef();
            File path = new File(SAVE_PATH_PREFIX + fileRef);
            String[] fns = path.list();
            if (fns != null) {
                accountNoticeInfo.setFiles(Arrays.stream(fns)
                        .map(s -> URI_PATH_PREFIX + fileRef + File.separatorChar + s)
                        .collect(Collectors.toList()));
            }
        });
        tracerUtil.newSpan("info", span -> {
            NoticeInfoExample infoExample = new NoticeInfoExample();
            infoExample.createCriteria().andNidEqualTo(nid);
            accountNoticeInfo.setNoticeInfos(
                    infoMapper.selectByExample(infoExample).stream().map(noticeInfo -> {
                        AccountNoticeInfo.Info info = new AccountNoticeInfo.Info();
                        info.withNid(noticeInfo.getNid()).withRef(noticeInfo.getRef());
                        info.setName(cacheService.getCollectName(noticeInfo.getRef()));
                        return info;
                    }).collect(Collectors.toList())
            );
        });
        if (accountNoticeInfo.getOrganization() != 0) {
            accountNoticeInfo.setOrganizationName(cacheService.getOrganizationName(accountNoticeInfo.getOrganization()));
        }
        accountNoticeInfo.setCreatorName(cacheService.getAccountNameByID(accountNoticeInfo.getCreator()));
        tracerUtil.newSpan("write cache", span -> {
            try {
                noticeOps.set(objectMapper.writeValueAsString(accountNoticeInfo), 1, TimeUnit.DAYS);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
        return accountNoticeInfo;
    }

    @Override
    @NewSpan("set account operation")
    public AccountNotice getNoticeAccountOperation(long nid, @SpanTag("aid") String aid) {
        AccountNotice accountNotice = accountNoticeMapper.selectByPrimaryKey(new AccountNoticeKey().withAid(aid).withNid(nid));
        if (accountNotice != null && !accountNotice.getLooked()) {
            accountNotice.setLooked(true);
            accountNoticeMapper.updateByPrimaryKey(accountNotice);
        }
        return accountNotice;
    }

    @Override
    @NewSpan("update account operation")
    public boolean updateAccountOperation(AccountNotice accountNotice) {
        return accountNoticeMapper.updateByPrimaryKeySelective(accountNotice) == 1;
    }

    @Override
    @NewSpan("get all account operation")
    public List<AccountNotice> getAllAccountOperationByNid(@SpanTag("id") long nid) {
        AccountNoticeExample example = new AccountNoticeExample();
        example.createCriteria().andNidEqualTo(nid);
        return accountNoticeMapper.selectByExample(example);
    }

    @Override
    @NewSpan("get notice")
    public Long getNoticeIdByRef(@SpanTag("todo ref") String ref) {
        NoticeExample example = new NoticeExample();
        example.createCriteria().andRefEqualTo(ref);
        List<Notice> notices = noticeMapper.selectByExample(example);
        if (notices.isEmpty()) {
            return null;
        }
        return notices.get(0).getId();
    }

    @Override
    @NewSpan("get notice")
    public List<String> getRefByCreator(@SpanTag("creator") String aid) {
        NoticeExample example = new NoticeExample();
        example.createCriteria().andCreatorEqualTo(aid);
        return noticeMapper.selectByExample(example)
                .stream()
                .map(Notice::getRef)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    @NewSpan("update notice")
    public void update(NoticeInfo notice, NoticeInfo oldNotice) {
        long id = oldNotice.getId();
        tracerUtil.newSpan("update notice", span -> {
            notice.setId(id);
            notice.setVersion(oldNotice.getVersion() + 1);
            // immutable
            notice.setCreator(null);
            notice.setOrganization(null);
            notice.setCreateTime(null);
            notice.setRef(null);
            noticeMapper.updateByPrimaryKeySelective(notice);
        });
        CountDownLatch countDownLatch = new CountDownLatch(3);
        tracerUtil.newSpanAsync("update tag", countDownLatch, span -> {
            Set<String> newTag = new HashSet<>(notice.getTag());
            newTag.removeAll(oldNotice.getTag());
            span.tag("add", newTag.toString());
            tagService.addTag(new ArrayList<>(newTag), id);
            Set<String> delTag = new HashSet<>(oldNotice.getTag());
            delTag.removeAll(notice.getTag());
            span.tag("delete", delTag.toString());
            delTag.stream()
                    .map(s -> new NoticeTagKey().withNid(id).withTag(s))
                    .forEach(noticeTagMapper::deleteByPrimaryKey);
        });
        tracerUtil.newSpanAsync("update importance", countDownLatch, span -> {
            if (Objects.equals(notice.getImportance(), oldNotice.getImportance())) {
                return;
            }
            AccountNoticeExample example = new AccountNoticeExample();
            example.createCriteria().andNidEqualTo(notice.getId());
            accountNoticeMapper.selectByExample(example)
                    .stream()
                    .peek(accountNotice -> accountNotice.setNoticeImportance(notice.getImportance()))
                    .forEach(accountNoticeMapper::updateByPrimaryKeySelective);
        });
        tracerUtil.newSpanAsync("write log", countDownLatch, span -> {
            NoticeUpdateLog log = new NoticeUpdateLog();
            log.setId(id);
            log.setVersion(oldNotice.getVersion());
            log.setUpdateTime(new Date());
            log.setPublicType(oldNotice.getPublicType());
            log.setTitle(oldNotice.getTitle());
            log.setContent(oldNotice.getContent());
            log.setContentType(oldNotice.getContentType());
            log.setImportance(oldNotice.getImportance());
            log.setStartTime(oldNotice.getStartTime());
            log.setEndTime(oldNotice.getEndTime());
            updateMapper.insert(log);
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        tracerUtil.newSpan("clear cache", span -> {
            redisTemplate.delete(NOTICE_PREFIX + oldNotice.getId());
            if (notice.getVisibility() == NoticeConst.VISIBILITY_PUBLIC) {
                publishNoticeChannel.send(MessageBuilder.withPayload(notice.getId()).build());
            }
        });
    }

    @Override
    @NewSpan("update log")
    public List<NoticeUpdateLog> updateLog(long id) {
        NoticeUpdateLogExample example = new NoticeUpdateLogExample();
        example.createCriteria().andIdEqualTo(id);
        return updateMapper.selectByExampleWithBLOBs(example);
    }

}
