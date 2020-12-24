package campuslifecenter.notice.model;

import campuslifecenter.notice.entry.AccountNotice;
import campuslifecenter.notice.entry.Notice;
import campuslifecenter.notice.entry.NoticeTagKey;
import campuslifecenter.notice.entry.NoticeTodo;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AccountNoticeInfo implements Serializable {

    @ApiModelProperty("通知信息")
    private Notice notice;

    @ApiModelProperty("账户操作信息")
    private AccountNotice accountOperation;

    @ApiModelProperty("标签列表")
    private List<String> tag;

    @ApiModelProperty("其他操作")
    private List<NoticeTodo> todoList;

    public static AccountNoticeInfo createByNotice(Notice notice) {
        return new AccountNoticeInfo()
                .setNotice(notice);
    }

    public static AccountNoticeInfo createByAccountNotice(AccountNotice notice) {
        return new AccountNoticeInfo()
                .setAccountOperation(notice);
    }

    public Notice getNotice() {
        return notice;
    }

    public AccountNoticeInfo setNotice(Notice notice) {
        this.notice = notice;
        return this;
    }

    public AccountNotice getAccountOperation() {
        return accountOperation;
    }

    public AccountNoticeInfo setAccountOperation(AccountNotice accountOperation) {
        this.accountOperation = accountOperation;
        return this;
    }

    public List<String> getTag() {
        return tag;
    }

    public AccountNoticeInfo withNoticeTag(List<NoticeTagKey> tag) {
        this.tag = tag.stream().map(NoticeTagKey::getTag).collect(Collectors.toList());
        return this;
    }

    public AccountNoticeInfo setTag(List<String> tag) {
        this.tag = tag;
        return this;
    }

    public List<NoticeTodo> getTodoList() {
        return todoList;
    }

    public AccountNoticeInfo setTodoList(List<NoticeTodo> todoList) {
        this.todoList = todoList;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AccountNoticeInfo that = (AccountNoticeInfo) o;
        return Objects.equals(notice.getId(), that.notice.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(notice.getId());
    }
}
