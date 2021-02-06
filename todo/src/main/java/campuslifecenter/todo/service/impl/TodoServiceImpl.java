package campuslifecenter.todo.service.impl;


import campuslifecenter.todo.entry.*;
import campuslifecenter.todo.mapper.AccountTodoMapper;
import campuslifecenter.todo.mapper.TodoMapper;
import campuslifecenter.todo.model.AccountTodoInfo;
import campuslifecenter.todo.model.AddTodoRequest;
import campuslifecenter.todo.service.TodoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.cloud.sleuth.annotation.SpanTag;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoMapper todoMapper;
    @Autowired
    private AccountTodoMapper accountTodoMapper;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Value("${todo.redis.cache.ref-todo}")
    private String REF_TODO_PREFIX;
    private ObjectMapper objectMapper = new ObjectMapper();


    @Override
    @NewSpan("update todo")
    public boolean update(AccountTodo accountTodo) {
        return accountTodoMapper.updateByPrimaryKey(accountTodo) == 1;
    }

    @Override
    @NewSpan("get account todos")
    public List<AccountTodo> getTodoByAccount(@SpanTag("id") String aid) {
        AccountTodoExample example = new AccountTodoExample();
        example.createCriteria().andAidEqualTo(aid);
        return accountTodoMapper.selectByExample(example);
    }

    @Override
    @NewSpan("get todo")
    public Todo getTodoById(@SpanTag("id") long id) {
        return todoMapper.selectByPrimaryKey(id);
    }

    @Override
    @NewSpan("get ref todos")
    public List<AccountTodoInfo> getTodoByRef(@SpanTag("ref") String ref) {
        return getTodoListBySource(ref)
                .stream()
                .flatMap(todo -> {
                    AccountTodoExample example = new AccountTodoExample();
                    example.createCriteria().andIdEqualTo(todo.getId());
                    return accountTodoMapper.selectByExample(example)
                            .stream()
                            .map(accountTodo -> new AccountTodoInfo().setAccountNoticeTodo(accountTodo))
                            .peek(accountTodoInfo -> accountTodoInfo.setTodo(todo));
                })
                .collect(Collectors.toList());
    }

    @Override
    @NewSpan("get account ref todo")
    public List<AccountTodoInfo> getTodoByAccountAndRef(@SpanTag("id") String aid, @SpanTag("ref") String source) {
        return getTodoListBySource(source)
                .stream()
                .map(todo -> new AccountTodoInfo().setAccountNoticeTodo(accountTodoMapper.selectByPrimaryKey(
                        new AccountTodoKey().withAid(aid).withId(todo.getId())
                )).setTodo(todo))
                .collect(Collectors.toList());
    }

    private List<Todo> getTodoListBySource(String source) {
        BoundValueOperations<String, String> todoOps = redisTemplate.boundValueOps(REF_TODO_PREFIX + source);
        if (todoOps.get() != null) {
            try {
                JavaType type = objectMapper.getTypeFactory().constructParametricType(List.class, Todo.class);
                return objectMapper.readValue(todoOps.get(), type);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        TodoExample todoExample = new TodoExample();
        todoExample.createCriteria().andRefEqualTo(source);
        List<Todo> todoList = todoMapper.selectByExample(todoExample);
        try {
            todoOps.set(objectMapper.writeValueAsString(todoList), 1, TimeUnit.DAYS);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return todoList;
    }

    @Override
    @NewSpan("add")
    public String add(AddTodoRequest addBody) {
        String source = UUID.randomUUID().toString();
        addBody.getValues().forEach(value-> {
            Todo todo = new Todo()
                    .withTitle(value)
                    .withRef(source);
            todoMapper.insert(todo);
            addBody.getAids().stream().distinct().forEach(aid -> accountTodoMapper.insert(
                    (AccountTodo) new AccountTodo().withTop(false).withAddList(false).withFinish(false)
                            .withAid(aid).withId(todo.getId())
            ));
        });
        return source;
    }

    @Override
    public boolean updateAccount(String ref, List<String> aids) {
        TodoExample example = new TodoExample();
        example.createCriteria().andRefEqualTo(ref);
        todoMapper.selectByExample(example)
                .stream()
                .map(Todo::getId)
                .forEach(id -> aids.stream().distinct().forEach(aid -> accountTodoMapper.insert(
                        (AccountTodo) new AccountTodo().withTop(false).withAddList(false).withFinish(false)
                                .withAid(aid).withId(id)
                )));
        return true;
    }

    @Override
    @NewSpan("select")
    public List<String> select(long id, boolean finish) {
        AccountTodoExample example = new AccountTodoExample();
        example.createCriteria().andIdEqualTo(id).andFinishEqualTo(finish);
        return accountTodoMapper.selectByExample(example)
                .stream().map(AccountTodoKey::getAid).collect(Collectors.toList());
    }

    @Override
    @NewSpan("get refs todo")
    public List<Todo> getTodoByRefs(List<String> refs) {
        return refs
                .stream()
                .flatMap(s -> getTodoListBySource(s).stream())
                .collect(Collectors.toList());
    }

}
