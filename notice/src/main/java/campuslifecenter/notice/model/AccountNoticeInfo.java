package campuslifecenter.notice.model;

import campuslifecenter.notice.entry.*;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AccountNoticeInfo extends Notice implements Serializable {

    @ApiModelProperty("账户id")
    private String aid;

    @ApiModelProperty("创建者名")
    private String creatorName;

    @ApiModelProperty("组织名")
    private String organizationName;

    @ApiModelProperty("是否已读")
    private Boolean isRead;

    @ApiModelProperty("是否置顶")
    private Boolean isTop;

    @ApiModelProperty("是否删除")
    private Boolean isDelete;

    @ApiModelProperty("相对重要度")
    private Integer relativeImportance;

    @ApiModelProperty("标签列表")
    private List<String> tag;

    @ApiModelProperty("其他操作")
    private List<AccountTodo> todoList;

    public static class AccountTodo extends AccountNoticeTodo implements Serializable {

        private Integer type;
        private String value;

        public Integer getType() {
            return type;
        }

        public AccountTodo setType(Integer type) {
            this.type = type;
            return this;
        }

        public String getValue() {
            return value;
        }

        public AccountTodo setValue(String value) {
            this.value = value;
            return this;
        }

        public AccountTodo setNoticeTodo(NoticeTodo noticeTodo) {
            setNid(noticeTodo.getNid());
            setId(noticeTodo.getId());
            setValue(noticeTodo.getTypeValue());
            setType(noticeTodo.getType());
            return this;
        }

        public AccountTodo setAccountNoticeTodo(AccountNoticeTodo accountNoticeTodo) {
            setId(accountNoticeTodo.getId());
            setNid(accountNoticeTodo.getNid());
            setAid(accountNoticeTodo.getAid());
            setIsAdd(accountNoticeTodo.getIsAdd());
            setIsTop(accountNoticeTodo.getIsTop());
            setIsFinish(accountNoticeTodo.getIsFinish());
            return this;
        }
    }

    public static AccountNoticeInfo createByNotice(Notice notice) {
        return new AccountNoticeInfo()
                .setNotice(notice);
    }

    public static AccountNoticeInfo createByAccountNotice(AccountNotice notice) {
        return new AccountNoticeInfo()
                .setAccountOperation(notice);
    }

    public void merge(AccountNoticeInfo other) {
        // notice
        setId(Optional.ofNullable(other.getId()).orElse(getId()));
        setCreator(Optional.ofNullable(other.getCreator()).orElse(getCreator()));
        setOrganization(Optional.ofNullable(other.getOrganization()).orElse(getOrganization()));
        setVisibility(Optional.ofNullable(other.getVisibility()).orElse(getVisibility()));
        setImportance(Optional.ofNullable(other.getImportance()).orElse(getImportance()));
        setPublicType(Optional.ofNullable(other.getPublicType()).orElse(getPublicType()));
        setTitle(Optional.ofNullable(other.getTitle()).orElse(getTitle()));
        setCreateTime(Optional.ofNullable(other.getCreateTime()).orElse(getCreateTime()));
        setStartTime(Optional.ofNullable(other.getStartTime()).orElse(getStartTime()));
        setEndTime(Optional.ofNullable(other.getEndTime()).orElse(getEndTime()));
        setContent(Optional.ofNullable(other.getContent()).orElse(getContent()));
        // account
        setAid(Optional.ofNullable(other.getAid()).orElse(getAid()));
        setRead(Optional.ofNullable(other.getRead()).orElse(getRead()));
        setTop(Optional.ofNullable(other.getTop()).orElse(getTop()));
        setDelete(Optional.ofNullable(other.getDelete()).orElse(getDelete()));
        // other
        setOrganizationName(Optional.ofNullable(other.getOrganizationName()).orElse(getOrganizationName()));
        setCreatorName(Optional.ofNullable(other.getCreatorName()).orElse(getCreatorName()));
        setRelativeImportance(Optional.ofNullable(other.getRelativeImportance()).orElse(getRelativeImportance()));
        setTag(Stream.concat(getTag().stream(), other.getTag().stream()).distinct().collect(Collectors.toList()));
        setTodoList(Stream.concat(getTodoList().stream(), other.getTodoList().stream()).distinct().collect(Collectors.toList()));
    }


    public AccountNoticeInfo setNotice(Notice notice) {
        setId(notice.getId());
        setCreator(notice.getCreator());
        setOrganization(notice.getOrganization());
        setVisibility(notice.getVisibility());
        setImportance(notice.getImportance());
        setPublicType(notice.getPublicType());
        setTitle(notice.getTitle());
        setCreateTime(notice.getCreateTime());
        setStartTime(notice.getStartTime());
        setEndTime(notice.getEndTime());
        setContent(notice.getContent());
        return this;
    }

    public AccountNoticeInfo setAccountOperation(AccountNotice accountOperation) {
        setAid(accountOperation.getAid());
        setId(accountOperation.getNid());
        setRead(accountOperation.getIsRead());
        setTop(accountOperation.getIsTop());
        setDelete(accountOperation.getIsDelete());
        setRelativeImportance(accountOperation.getRelativeImportance());
        return this;
    }

    public AccountNoticeInfo withNoticeTag(List<NoticeTagKey> tag) {
        this.tag = tag.stream().map(NoticeTagKey::getTag).collect(Collectors.toList());
        return this;
    }
    public String getAid() {
        return aid;
    }

    public AccountNoticeInfo setAid(String aid) {
        this.aid = aid;
        return this;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public AccountNoticeInfo setCreatorName(String creatorName) {
        this.creatorName = creatorName;
        return this;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public AccountNoticeInfo setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
        return this;
    }

    public Boolean getRead() {
        return isRead;
    }

    public AccountNoticeInfo setRead(Boolean read) {
        isRead = read;
        return this;
    }

    public Boolean getTop() {
        return isTop;
    }

    public AccountNoticeInfo setTop(Boolean top) {
        isTop = top;
        return this;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public AccountNoticeInfo setDelete(Boolean delete) {
        isDelete = delete;
        return this;
    }

    public Integer getRelativeImportance() {
        return relativeImportance;
    }

    public AccountNoticeInfo setRelativeImportance(Integer relativeImportance) {
        this.relativeImportance = relativeImportance;
        return this;
    }

    public List<String> getTag() {
        return tag == null ? new ArrayList<>() : tag;
    }

    public AccountNoticeInfo setTag(List<String> tag) {
        this.tag = tag;
        return this;
    }

    public List<AccountTodo> getTodoList() {
        return todoList == null ? new ArrayList<>() : todoList;
    }

    public AccountNoticeInfo setTodoList(List<AccountTodo> todoList) {
        this.todoList = todoList;
        return this;
    }


}
