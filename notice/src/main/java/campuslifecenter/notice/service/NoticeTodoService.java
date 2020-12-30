package campuslifecenter.notice.service;

import campuslifecenter.notice.entry.NoticeTodo;
import campuslifecenter.notice.entry.NoticeTodoKey;
import campuslifecenter.notice.model.AccountNoticeInfo;

import java.util.List;

public interface NoticeTodoService {

    List<NoticeTodo> getTodoByNoticeId(long id);

    List<String> getTodoAccountIdByNoticeId(NoticeTodoKey key, boolean finish);

    List<AccountNoticeInfo.AccountTodo> getTodoByAccount(String aid);

}
