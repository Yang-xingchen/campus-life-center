package campuslifecenter.usercenter.service.impl;

import campuslifecenter.common.model.ConditionAccountUpdate;
import campuslifecenter.usercenter.component.AccountStream;
import campuslifecenter.usercenter.entry.AccountOrganization;
import campuslifecenter.usercenter.entry.ConditionOrganization;
import campuslifecenter.usercenter.entry.ConditionOrganizationExample;
import campuslifecenter.usercenter.mapper.ConditionOrganizationMapper;
import campuslifecenter.usercenter.service.ConditionService;
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
    @Qualifier(AccountStream.CONDITION_CHANGE)
    private MessageChannel conditionChannel;

    @Autowired
    private ConditionOrganizationMapper conditionMapper;

    @Override
    public void update(AccountOrganization organization) {
        ConditionAccountUpdate update = new ConditionAccountUpdate();
        update.setAid(organization.getAid());
        ConditionOrganizationExample example = new ConditionOrganizationExample();
        example.createCriteria().andOidEqualTo(organization.getOid()).andBelongEqualTo(true);
        List<String> refs = conditionMapper.selectByExample(example).stream().map(ConditionOrganization::getRef).collect(Collectors.toList());
        if (refs.isEmpty()) {
            return;
        }
        update.setRefs(refs);
        conditionChannel.send(MessageBuilder.withPayload(update).build());
    }


}
