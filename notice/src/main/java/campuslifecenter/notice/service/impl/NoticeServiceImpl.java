package campuslifecenter.notice.service.impl;

import campuslifecenter.common.component.TracerUtil;
import campuslifecenter.notice.entry.*;
import campuslifecenter.notice.mapper.*;
import campuslifecenter.notice.model.*;
import campuslifecenter.notice.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.cloud.sleuth.annotation.SpanTag;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.*;
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
                throw new IllegalArgumentException("notice not found: " + nid);
            }
            return AccountNoticeInfo.createByNotice(notice);
        });
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
        accountNoticeInfo.setOrganizationName(cacheService.getOrganizationName(accountNoticeInfo.getOrganization()));
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
        return accountNoticeMapper.selectByPrimaryKey(new AccountNoticeKey().withAid(aid).withNid(nid));
    }

    @Override
    @NewSpan("update account operation")
    public boolean updateAccountOperation(AccountNotice accountNotice) {
        return accountNoticeMapper.updateByPrimaryKey(accountNotice) == 1;
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
    public List<String> getTodoRefByCreator(@SpanTag("creator") String aid) {
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
        tracerUtil.newSpan("update tag", span -> {
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
        tracerUtil.newSpan("write log", span -> {
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
        tracerUtil.newSpan("clear cache", span -> {
            redisTemplate.delete(NOTICE_PREFIX + oldNotice.getId());
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
