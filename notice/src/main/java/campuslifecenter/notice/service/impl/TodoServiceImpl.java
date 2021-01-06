package campuslifecenter.notice.service.impl;

import campuslifecenter.notice.entry.*;
import campuslifecenter.notice.mapper.AccountNoticeTodoMapper;
import campuslifecenter.notice.mapper.NoticeTodoMapper;
import campuslifecenter.notice.model.AccountNoticeInfo;
import campuslifecenter.notice.model.AccountTodo;
import campuslifecenter.notice.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class TodoServiceImpl implements TodoService {

    @Autowired
    private AccountNoticeTodoMapper accountNoticeTodoMapper;
    @Autowired
    private NoticeTodoMapper noticeTodoMapper;

    @Override
    public AccountNoticeTodo getAccountTodoOperation(long nid, int id, String aid) {
        return null;
    }

    @Override
    public void setAccountTodoOperation(AccountNoticeInfo noticeInfo, String aid) {
        noticeInfo.getTodoList().forEach(accountTodo -> {
            AccountNoticeTodoKey key = new AccountNoticeTodoKey();
            key.withNid(accountTodo.getNid()).withAid(aid).withId(accountTodo.getId());
            accountTodo.setAccountNoticeTodo(accountNoticeTodoMapper.selectByPrimaryKey(key));
        });
    }

    @Override
    public List<AccountTodo> getAccountTodoByNid(long nid) {
        AccountNoticeTodoExample example = new AccountNoticeTodoExample();
        example.createCriteria().andNidEqualTo(nid);
        return accountNoticeTodoMapper
                .selectByExample(example)
                .stream()
                .map(accountNoticeTodo -> new AccountTodo().setAccountNoticeTodo(accountNoticeTodo))
                .peek(todo -> todo.setNoticeTodo(noticeTodoMapper.selectByPrimaryKey(
                        new NoticeTodoKey().withId(todo.getId()).withNid(nid)
                )))
                .collect(Collectors.toList());
    }

}
