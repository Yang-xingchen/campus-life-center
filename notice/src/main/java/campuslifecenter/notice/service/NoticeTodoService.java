package campuslifecenter.notice.service;

import campuslifecenter.notice.entry.NoticeTodo;
import campuslifecenter.notice.entry.NoticeTodoKey;

import java.util.List;

public interface NoticeTodoService {

    List<NoticeTodo> getButtonByNoticeId(long id);

    List<String> getTodoAccountIdByNoticeId(NoticeTodoKey key, boolean finish);

}
