package campuslifecenter.notice.component;

import campuslifecenter.common.component.TracerUtil;
import campuslifecenter.notice.entry.Notice;
import campuslifecenter.notice.mapper.NoticeMapper;
import campuslifecenter.notice.model.IdName;
import campuslifecenter.notice.service.PublishAccountService;
import campuslifecenter.notice.service.PublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static campuslifecenter.notice.model.NoticeConst.STATUS_PUBLISHED;

@Component
@Transactional(rollbackFor = RuntimeException.class)
public class PublishAccountListener {

    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    private PublishService publishService;
    @Autowired
    private PublishAccountService publishAccountService;

    @Autowired
    private TracerUtil tracerUtil;

    /**
     * 处理发布
     * 观察者模式-观察者
     * @param id
     */
    @NewSpan("publish account")
    @StreamListener(NoticeStream.PUBLISH_ACCOUNT)
    public void processPublish(long id) {
        Notice notice = noticeMapper.selectByPrimaryKey(id);
        // 已发布
        if (notice.getPublishStatus() == STATUS_PUBLISHED) {
            return;
        }
        List<String> aids = tracerUtil.newSpan("get account list", scopedSpan -> {
            return publishAccountService
                    .getPublishByNid(id, false)
                    .stream()
                    .flatMap(publishAccount -> publishAccount.getAccounts().stream())
                    .map(IdName::getId)
                    .distinct()
                    .collect(Collectors.toList());
        });
        publishService.publishNoticeAccount(notice, aids);
    }

}
