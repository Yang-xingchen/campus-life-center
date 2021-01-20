package campuslifecenter.notice.service.impl;

import campuslifecenter.notice.component.NoticeStream;
import campuslifecenter.notice.component.Util;
import campuslifecenter.notice.entry.*;
import campuslifecenter.notice.mapper.*;
import campuslifecenter.notice.model.*;
import campuslifecenter.notice.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private Util util;

    public ObjectMapper objectMapper = new ObjectMapper();

    @Value("${notice.cache.notice}")
    public String NOTICE_PREFIX;
    @Value("${notice.notice-file-path}")
    public String NOTICE_FILE_PATH_PREFIX;
    @Value("${notice.web-file-path}")
    public String WEB_FILE_PATH_PREFIX;

    @Override
    public List<AccountNoticeInfo> getAllNoticeOperationByAid(String aid) {
        AccountNoticeExample noticeExample = new AccountNoticeExample();
        noticeExample.createCriteria().andAidEqualTo(aid);
        return accountNoticeMapper
                .selectByExample(noticeExample)
                .stream()
                .map(AccountNoticeInfo::createByAccountNotice)
                .collect(Collectors.toList());
    }

    @Override
    public AccountNoticeInfo getNoticeById(long nid) {
        BoundValueOperations<String, String> noticeOps = redisTemplate.boundValueOps(NOTICE_PREFIX + nid);
        if (noticeOps.get() != null) {
            try {
                return objectMapper.readValue(noticeOps.get(), AccountNoticeInfo.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        AccountNoticeInfo accountNoticeInfo = util.newSpan("notice", span -> {
            Notice notice = noticeMapper.selectByPrimaryKey(nid);
            if (notice == null) {
                throw new IllegalArgumentException("notice not found: " + nid);
            }
            return AccountNoticeInfo.createByNotice(notice);
        });
        util.newSpan("tag", span -> {
            NoticeTagExample tagExample = new NoticeTagExample();
            tagExample.createCriteria().andNidEqualTo(nid);
            accountNoticeInfo.withNoticeTag(noticeTagMapper.selectByExample(tagExample));
        });
        util.newSpan("file", span -> {
            String fileRef = accountNoticeInfo.getFileRef();
            if (fileRef != null) {
                File path = new File(NOTICE_FILE_PATH_PREFIX + fileRef);
                String[] fns = path.list();
                if (fns != null) {
                    accountNoticeInfo.setFiles(Arrays.stream(fns)
                            .map(s -> WEB_FILE_PATH_PREFIX + fileRef + "/" + s)
                            .collect(Collectors.toList()));
                }
            }
        });
        util.newSpan("info", span -> {
            NoticeInfoExample infoExample = new NoticeInfoExample();
            infoExample.createCriteria().andNidEqualTo(nid);
            accountNoticeInfo.setNoticeInfos(infoMapper.selectByExample(infoExample));
        });
        setCreatorName(accountNoticeInfo);
        setOrganizationName(accountNoticeInfo);
        util.newSpan("write cache", span -> {
            try {
                noticeOps.set(objectMapper.writeValueAsString(accountNoticeInfo), 1, TimeUnit.DAYS);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
        return accountNoticeInfo;
    }

    @Override
    public AccountNoticeInfo setNoticeAccountOperation(AccountNoticeInfo noticeInfo, String aid) {
        return noticeInfo.setAccountOperation(accountNoticeMapper.selectByPrimaryKey(
                new AccountNoticeKey().withAid(aid).withNid(noticeInfo.getId())
        ));
    }

    @Override
    public boolean updateAccountOperation(AccountNotice accountNotice) {
        return accountNoticeMapper.updateByPrimaryKey(accountNotice) == 1;
    }

    private AccountNoticeInfo setOrganizationName(AccountNoticeInfo info) {
        return info.setOrganizationName(cacheService.getOrganizationName(info.getOrganization()));
    }

    private AccountNoticeInfo setCreatorName(AccountNoticeInfo info) {
        return info.setCreatorName(cacheService.getAccountNameByID(info.getCreator()));
    }

    @Override
    public List<AccountNotice> getAllAccountOperationByNid(long nid) {
        AccountNoticeExample example = new AccountNoticeExample();
        example.createCriteria().andNidEqualTo(nid);
        return accountNoticeMapper.selectByExample(example);
    }

    @Override
    public Long getNoticeIdByTodoRef(String ref) {
        NoticeExample example = new NoticeExample();
        example.createCriteria().andTodoRefEqualTo(ref);
        List<Notice> notices = noticeMapper.selectByExample(example);
        if (notices.size() != 1) {
            throw new IllegalArgumentException("ref not only: ref=" + ref);
        }
        return notices.get(0).getId();
    }

    @Override
    public List<String> getTodoRefByCreator(String aid) {
        NoticeExample example = new NoticeExample();
        example.createCriteria().andCreatorEqualTo(aid);
        return noticeMapper.selectByExample(example)
                .stream()
                .map(Notice::getTodoRef)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

}
