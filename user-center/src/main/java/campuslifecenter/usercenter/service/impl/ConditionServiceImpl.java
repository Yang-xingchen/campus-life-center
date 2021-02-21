package campuslifecenter.usercenter.service.impl;

import campuslifecenter.common.exception.ResponseException;
import campuslifecenter.common.model.ConditionAccountUpdate;
import campuslifecenter.usercenter.component.AccountStream;
import campuslifecenter.usercenter.entry.*;
import campuslifecenter.usercenter.mapper.ConditionAccountMapper;
import campuslifecenter.usercenter.mapper.ConditionOrganizationMapper;
import campuslifecenter.usercenter.service.ConditionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class ConditionServiceImpl implements ConditionService {


    @Autowired
    @Qualifier(AccountStream.CONDITION_CHANGE)
    private MessageChannel conditionChannel;

    @Autowired
    private ConditionOrganizationMapper organizationMapper;
    @Autowired
    private ConditionAccountMapper accountMapper;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Value("${user-center.redis.condition}")
    private String CONDITION_PREFIX;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void update(AccountOrganization organization) {
        ConditionAccountUpdate update = new ConditionAccountUpdate();
        update.setAid(organization.getAid());
        ConditionOrganizationExample example = new ConditionOrganizationExample();
        example.createCriteria().andOidEqualTo(organization.getOid()).andBelongEqualTo(true);
        List<String> refs = organizationMapper.selectByExample(example)
                .stream()
                .map(ConditionOrganization::getRef)
                .collect(Collectors.toList());
        if (refs.isEmpty()) {
            return;
        }
        update.setRefs(refs);
        conditionChannel.send(MessageBuilder.withPayload(update).build());
    }

    @Override
    public List<String> getAccounts(String ref) {
        ConditionAccountExample example = new ConditionAccountExample();
        example.createCriteria().andRefEqualTo(ref);
        return accountMapper.selectByExample(example)
                .stream()
                .map(ConditionAccountKey::getAid)
                .collect(Collectors.toList());
    }

    @Override
    public String create(List<String> aid) {
        String uuid = UUID.randomUUID().toString();
        String cache;
        try {
            cache = objectMapper.writeValueAsString(aid);
        } catch (JsonProcessingException e) {
            throw new ResponseException(e);
        }
        redisTemplate.opsForValue().set(CONDITION_PREFIX + uuid, cache, 1, TimeUnit.DAYS);
        return uuid;
    }


}
