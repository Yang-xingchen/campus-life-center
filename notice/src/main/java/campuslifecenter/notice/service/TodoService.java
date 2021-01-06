package campuslifecenter.notice.service;

import campuslifecenter.notice.entry.AccountNoticeTodo;
import campuslifecenter.notice.model.AccountNoticeInfo;
import campuslifecenter.notice.model.AccountTodo;

import java.util.List;

public interface TodoService {

    AccountNoticeTodo getAccountTodoOperation(long nid, int id, String aid);


    void setAccountTodoOperation(AccountNoticeInfo noticeInfo, String aid);

    List<AccountTodo> getAccountTodoByNid(long nid);

}
