package campuslifecenter.notice.component;

import campuslifecenter.common.component.TracerUtil;
import campuslifecenter.common.model.ConditionAccountUpdate;
import campuslifecenter.notice.entry.Notice;
import campuslifecenter.notice.entry.NoticeConditionExample;
import campuslifecenter.notice.mapper.*;
import campuslifecenter.notice.service.PublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional(rollbackFor = RuntimeException.class)
public class PublishObserveListener {

    @Autowired
    private NoticeMapper noticeMapper;
    @Autowired
    private PublishService publishService;
    @Autowired
    private NoticeConditionMapper conditionMapper;

    @Autowired
    private TracerUtil tracerUtil;

    /**
     * 处理动态发布
     * 观察者模式-观察者
     */
    @NewSpan("process observe")
    @StreamListener(NoticeStream.PUBLISH_OBSERVE)
    public void processObserve(ConditionAccountUpdate update) {
        NoticeConditionExample example = new NoticeConditionExample();
        example.createCriteria().andRefIn(update.getRefs());
        conditionMapper.selectByExample(example).forEach(noticeCondition -> {
            Notice notice = noticeMapper.selectByPrimaryKey(noticeCondition.getNid());
            publishService.publishNoticeAccount(notice, List.of(update.getAid()));
        });
    }

}
