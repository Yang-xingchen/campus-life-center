package campuslifecenter.todo.service.impl;

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
    @Qualifier(TodoStream.CONDITION_CHANGE)
    private MessageChannel conditionChannel;

    @Autowired
    private ConditionTodoMapper conditionMapper;
    @Autowired
    private AccountTodoMapper accountTodoMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Value("${todo.redis.condition}")
    private String CONDITION_PREFIX;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
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
        update.setRefs(refs);
        conditionChannel.send(MessageBuilder.withPayload(update).build());
    }

    @Override
    public List<String> getAccounts(String ref) {
        ConditionTodo todo = conditionMapper.selectByPrimaryKey(ref);
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


}
