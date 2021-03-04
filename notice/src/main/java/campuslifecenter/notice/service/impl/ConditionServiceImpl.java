package campuslifecenter.notice.service.impl;

import campuslifecenter.common.component.TracerUtil;
import campuslifecenter.common.exception.ProcessException;
import campuslifecenter.common.exception.ResponseException;
import campuslifecenter.common.model.ConditionAccountUpdate;
import campuslifecenter.notice.component.NoticeStream;
import campuslifecenter.notice.entry.AccountSubscribeKey;
import campuslifecenter.notice.entry.ConditionOrganization;
import campuslifecenter.notice.entry.ConditionOrganizationExample;
import campuslifecenter.notice.mapper.ConditionOrganizationMapper;
import campuslifecenter.notice.service.ConditionService;
import campuslifecenter.notice.service.OrganizationService;
import campuslifecenter.notice.service.OrganizationSubscribeService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class ConditionServiceImpl implements ConditionService {


    @Autowired
    @Qualifier(NoticeStream.CONDITION_CHANGE)
    private MessageChannel conditionChannel;

    @Autowired
    private ConditionOrganizationMapper conditionMapper;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private OrganizationSubscribeService organizationSubscribeService;

    @Autowired
    private TracerUtil tracerUtil;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Value("${notice.redis.condition}")
    private String CONDITION_PREFIX;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @NewSpan("condition change")
    public void update(AccountSubscribeKey subscribe) {
        ConditionAccountUpdate update = new ConditionAccountUpdate();
        update.setAid(subscribe.getAid());
        ConditionOrganizationExample example = new ConditionOrganizationExample();
        example.createCriteria()
                .andDynamicEqualTo(true)
                .andOidEqualTo(subscribe.getOid())
                .andSubscribeEqualTo(true);
        List<String> refs = conditionMapper.selectByExample(example).stream().map(ConditionOrganization::getRef).collect(Collectors.toList());
        if (refs.isEmpty()) {
            return;
        }
        tracerUtil.getSpan().tag("refs", refs.toString());
        update.setRefs(refs);
        conditionChannel.send(MessageBuilder.withPayload(update).build());
    }

    @Override
    @NewSpan("get accounts")
    public List<String> getAccounts(@SpanTag("ref") String ref) {
        BoundValueOperations<String, String> conditionOps = redisTemplate.boundValueOps(CONDITION_PREFIX + ref);
        ConditionOrganization organization = null;
        try {
            String cache = conditionOps.get();
            if (cache != null) {
                organization = objectMapper.readValue(cache, ConditionOrganization.class);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if (organization == null) {
            organization = conditionMapper.selectByPrimaryKey(ref);
        }
        return getAccounts(organization);
    }

    @Override
    public List<String> getAccounts(ConditionOrganization organization) {
        if (organization == null) {
            return List.of();
        }
        List<String> list = new ArrayList<>();
        if (organization.getBelong()) {
            list.addAll(organizationService.getMemberId(organization.getOid())
                    .checkGet(ProcessException.USER_CENTER, "get member fail"));
        }
        if (organization.getSubscribe()) {
            list.addAll(organizationSubscribeService.getSubscribeAccountId(organization.getOid()));
        }
        return list;
    }

    @Override
    public String create(ConditionOrganization conditionOrganization) {
        String uuid = UUID.randomUUID().toString();
        conditionOrganization.setRef(uuid);
        String cache;
        try {
            cache = objectMapper.writeValueAsString(conditionOrganization);
        } catch (JsonProcessingException e) {
            throw new ResponseException(e);
        }
        redisTemplate.opsForValue().set(CONDITION_PREFIX + uuid, cache, 1, TimeUnit.DAYS);
        return uuid;
    }

    @Override
    @NewSpan("publish")
    public boolean publish(@SpanTag("ref") String ref) {
        BoundValueOperations<String, String> conditionOps = redisTemplate.boundValueOps(CONDITION_PREFIX + ref);
        try {
            ConditionOrganization organization = objectMapper.readValue(conditionOps.get(), ConditionOrganization.class);
            conditionMapper.insert(organization);
            redisTemplate.delete(CONDITION_PREFIX + ref);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


}
