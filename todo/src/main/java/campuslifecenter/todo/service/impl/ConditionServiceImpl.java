package campuslifecenter.todo.service.impl;

import campuslifecenter.common.model.ConditionAccountUpdate;
import campuslifecenter.todo.component.TodoStream;
import campuslifecenter.todo.entry.AccountTodo;
import campuslifecenter.todo.entry.ConditionTodo;
import campuslifecenter.todo.entry.ConditionTodoExample;
import campuslifecenter.todo.mapper.ConditionTodoMapper;
import campuslifecenter.todo.service.ConditionService;
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
    @Qualifier(TodoStream.CONDITION_CHANGE)
    private MessageChannel conditionChannel;

    @Autowired
    private ConditionTodoMapper conditionMapper;

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


}
