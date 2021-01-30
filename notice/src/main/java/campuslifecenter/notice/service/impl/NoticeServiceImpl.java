package campuslifecenter.notice.service.impl;

import campuslifecenter.common.component.TracerUtil;
import campuslifecenter.common.exception.ProcessException;
import campuslifecenter.common.model.Response;
import campuslifecenter.notice.component.NoticeStream;
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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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
    private PublishTodoMapper publishTodoMapper;
    @Autowired
    private PublishInfoMapper publishInfoMapper;
    @Autowired
    private PublishOrganizationMapper publishOrganizationMapper;

    @Autowired
    private NoticeStream noticeStream;
    @Autowired
    private TodoService todoService;
    @Autowired
    private InformationService informationService;
    @Autowired
    private CacheService cacheService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private TracerUtil tracerUtil;

    public ObjectMapper objectMapper = new ObjectMapper();

    @Value("${notice.redis.cache.notice}")
    public String NOTICE_PREFIX;
    @Value("${notice.save-path}")
    public String SAVE_PATH_PREFIX;
    @Value("${notice.uri-path}")
    public String URI_PATH_PREFIX;

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
            String fileRef = accountNoticeInfo.getFileRef();
            if (fileRef != null) {
                File path = new File(SAVE_PATH_PREFIX + fileRef);
                String[] fns = path.list();
                if (fns != null) {
                    accountNoticeInfo.setFiles(Arrays.stream(fns)
                            .map(s -> URI_PATH_PREFIX + fileRef + "/" + s)
                            .collect(Collectors.toList()));
                }
            }
        });
        tracerUtil.newSpan("info", span -> {
            NoticeInfoExample infoExample = new NoticeInfoExample();
            infoExample.createCriteria().andNidEqualTo(nid);
            accountNoticeInfo.setNoticeInfos(
                    infoMapper.selectByExample(infoExample).stream().map(noticeInfo -> {
                        AccountNoticeInfo.Info info = new AccountNoticeInfo.Info();
                        info.withNid(noticeInfo.getNid()).withRef(noticeInfo.getRef());
                        Response<String> response = informationService.getRefName(noticeInfo.getRef());
                        ProcessException.check(ProcessException.INFO,"get info fail", response);
                        info.setName(response.getData());
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
    public AccountNoticeInfo setNoticeAccountOperation(AccountNoticeInfo noticeInfo, @SpanTag("aid") String aid) {
        return noticeInfo.setAccountOperation(accountNoticeMapper.selectByPrimaryKey(
                new AccountNoticeKey().withAid(aid).withNid(noticeInfo.getId())
        ));
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
    public Long getNoticeIdByTodoRef(@SpanTag("todo ref") String ref) {
        NoticeExample example = new NoticeExample();
        example.createCriteria().andTodoRefEqualTo(ref);
        List<Notice> notices = noticeMapper.selectByExample(example);
        if (notices.size() != 1) {
            throw new IllegalArgumentException("ref not only: ref=" + ref);
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
                .map(Notice::getTodoRef)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

}
