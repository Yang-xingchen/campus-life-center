package campuslifecenter.todo.service.impl;

import campuslifecenter.common.component.TracerUtil;
import campuslifecenter.common.exception.ProcessException;
import campuslifecenter.common.exception.ResponseException;
import campuslifecenter.common.model.ConditionAccountUpdate;
import campuslifecenter.todo.component.TodoStream;
import campuslifecenter.todo.entry.*;
import campuslifecenter.todo.mapper.AccountTodoMapper;
import campuslifecenter.todo.mapper.ConditionTodoMapper;
import campuslifecenter.todo.service.ConditionService;
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
    @Qualifier(TodoStream.CONDITION_CHANGE)
    private MessageChannel conditionChannel;

    @Autowired
    private ConditionTodoMapper conditionMapper;
    @Autowired
    private AccountTodoMapper accountTodoMapper;

    @Autowired
    private TracerUtil tracerUtil;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Value("${todo.redis.condition}")
    private String CONDITION_PREFIX;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @NewSpan("condition change")
    public void update(AccountTodo accountTodo) {
        if (accountTodo.getFinish() == null) {
            return;
        }
        ConditionAccountUpdate update = new ConditionAccountUpdate();
        update.setAid(accountTodo.getAid());
        ConditionTodoExample example = new ConditionTodoExample();
        example.createCriteria().andTidEqualTo(accountTodo.getId()).andFinishEqualTo(accountTodo.getFinish());
        List<String> refs = conditionMapper.selectByExample(example).stream().map(ConditionTodo::getRef).collect(Collectors.toList());
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
        ConditionTodo todo = null;
        try {
            String cache = conditionOps.get();
            if (cache != null) {
                todo = objectMapper.readValue(cache, ConditionTodo.class);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if (todo == null) {
            todo = conditionMapper.selectByPrimaryKey(ref);
        }
        if (todo == null) {
            return List.of();
        }
        AccountTodoExample example = new AccountTodoExample();
        example.createCriteria()
                .andIdEqualTo(todo.getTid())
                .andFinishEqualTo(todo.getFinish());
        return accountTodoMapper.selectByExample(example)
                .stream()
                .map(AccountTodoKey::getAid)
                .collect(Collectors.toList());
    }

    @Override
    public String create(ConditionTodo conditionTodo) {
        String uuid = UUID.randomUUID().toString();
        conditionTodo.setRef(uuid);
        String cache;
        try {
            cache = objectMapper.writeValueAsString(conditionTodo);
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
            ConditionTodo todo = objectMapper.readValue(conditionOps.get(), ConditionTodo.class);
            conditionMapper.insert(todo);
            redisTemplate.delete(CONDITION_PREFIX + ref);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
