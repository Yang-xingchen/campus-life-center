package campuslifecenter.notice.component;

import campuslifecenter.common.component.TracerUtil;
import campuslifecenter.notice.entry.*;
import campuslifecenter.notice.mapper.*;
import campuslifecenter.notice.model.PublishObserveRequest;
import campuslifecenter.notice.service.PublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Component
@Transactional(rollbackFor = RuntimeException.class)
public class PublishObserveListener {

    @Autowired
    private PublishOrganizationMapper organizationMapper;
    @Autowired
    private PublishTodoMapper todoMapper;
    @Autowired
    private PublishInfoMapper infoMapper;

    @Autowired
    private NoticeMapper noticeMapper;
    @Autowired
    private PublishService publishService;

    @Autowired
    private TracerUtil tracerUtil;

    private static final String[] TYPE_MAP = new String[]{"unknown", "organization", "todo", "info"};
    private final Map<Integer, Consumer<PublishObserveRequest>> CONSUMER_MAP = Map.of(
            1, this::handleOrganization,
            2, this::handleTodo,
            3, this::handleInfo
    );

    /**
     * 处理动态发布
     * 观察者模式-观察者
     */
    @NewSpan("process observe")
    @StreamListener(NoticeStream.PUBLISH_OBSERVE)
    public void processObserve(PublishObserveRequest request) {
        tracerUtil.getSpan().tag("type", TYPE_MAP[request.getType()]);
        CONSUMER_MAP.get(request.getType()).accept(request);
    }

    private void handleOrganization(PublishObserveRequest request) {
        tracerUtil.getSpan().tag("account", request.getAid());
        PublishOrganizationExample example = new PublishOrganizationExample();
        PublishOrganizationExample.Criteria criteria = example.createCriteria();
        criteria.andOidEqualTo(request.getOid()).andDynamicEqualTo(true);
        if (request.getBelong() != null && request.getBelong()) {
            criteria.andBelongEqualTo(request.getBelong());
        }
        if (request.getSubscribe() != null && request.getSubscribe()) {
            criteria.andSubscribeEqualTo(request.getSubscribe());
        }
        organizationMapper.selectByExample(example)
                .stream()
                .map(PublishOrganizationKey::getNid)
                .forEach(nid -> publishService.publishNoticeAccount(noticeMapper.selectByPrimaryKey(nid), List.of(request.getAid())));
    }

    private void handleTodo(PublishObserveRequest request) {
        PublishTodoExample example = new PublishTodoExample();
        example.createCriteria().andDynamicEqualTo(true)
                .andTidEqualTo(request.getTid())
                .andFinishEqualTo(request.getFinish());
        todoMapper.selectByExample(example)
                .stream()
                .map(PublishTodo::getNid)
                .forEach(nid -> publishService.publishNoticeAccount(noticeMapper.selectByPrimaryKey(nid), List.of(request.getAid())));
    }

    private void handleInfo(PublishObserveRequest request) {
        PublishInfoExample example = new PublishInfoExample();
        example.createCriteria().andDynamicEqualTo(true)
                .andIidEqualTo(request.getIid())
                .andTextEqualTo(request.getText());
        infoMapper.selectByExample(example)
                .stream()
                .map(PublishInfo::getNid)
                .forEach(nid -> publishService.publishNoticeAccount(noticeMapper.selectByPrimaryKey(nid), List.of(request.getAid())));
    }

}
