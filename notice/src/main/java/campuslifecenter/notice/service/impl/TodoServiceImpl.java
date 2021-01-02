package campuslifecenter.notice.service.impl;

import campuslifecenter.notice.entry.AccountNoticeTodo;
import campuslifecenter.notice.entry.AccountNoticeTodoKey;
import campuslifecenter.notice.mapper.AccountNoticeTodoMapper;
import campuslifecenter.notice.model.AccountNoticeInfo;
import campuslifecenter.notice.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class TodoServiceImpl implements TodoService {

    @Autowired
    private AccountNoticeTodoMapper accountNoticeTodoMapper;

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
}
