package campuslifecenter.notice.service.impl;

import campuslifecenter.common.model.ConditionAccountUpdate;
import campuslifecenter.notice.component.NoticeStream;
import campuslifecenter.notice.entry.AccountSubscribeKey;
import campuslifecenter.notice.entry.ConditionOrganization;
import campuslifecenter.notice.entry.ConditionOrganizationExample;
import campuslifecenter.notice.mapper.ConditionOrganizationMapper;
import campuslifecenter.notice.service.ConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConditionServiceImpl implements ConditionService {


    @Autowired
    @Qualifier(NoticeStream.CONDITION_CHANGE)
    private MessageChannel conditionChannel;

    @Autowired
    private ConditionOrganizationMapper conditionMapper;

    @Override
    public void update(AccountSubscribeKey subscribe) {
        ConditionAccountUpdate update = new ConditionAccountUpdate();
        update.setAid(subscribe.getAid());
        ConditionOrganizationExample example = new ConditionOrganizationExample();
        example.createCriteria().andOidEqualTo(subscribe.getOid()).andSubscribeEqualTo(true);
        List<String> refs = conditionMapper.selectByExample(example).stream().map(ConditionOrganization::getRef).collect(Collectors.toList());
        if (refs.isEmpty()) {
            return;
        }
        update.setRefs(refs);
        conditionChannel.send(MessageBuilder.withPayload(update).build());
    }


}
