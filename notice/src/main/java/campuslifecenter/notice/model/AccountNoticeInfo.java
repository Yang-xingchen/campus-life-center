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
    private Boolean looked;

    @ApiModelProperty("是否置顶")
    private Boolean top;

    @ApiModelProperty("是否删除")
    private Boolean del;

    @ApiModelProperty("相对重要度")
    private Integer relativeImportance;

    @ApiModelProperty("标签列表")
    private List<String> tag;

    @ApiModelProperty("待办列表")
    private List<AccountTodoInfo> todoList;

    @ApiModelProperty("信息填写")
    private List<NoticeInfo> noticeInfos;

    @ApiModelProperty("文件列表")
    private List<String> files;

    public static AccountNoticeInfo createByNotice(Notice notice) {
        Objects.requireNonNull(notice, "notice not find");
        return new AccountNoticeInfo()
                .setNotice(notice);
    }

    public static AccountNoticeInfo createByAccountNotice(AccountNotice notice) {
        Objects.requireNonNull(notice, "account notice not find");
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
        setVersion(Optional.ofNullable(other.getVersion()).orElse(getVersion()));
        setTitle(Optional.ofNullable(other.getTitle()).orElse(getTitle()));
        setContentType(Optional.ofNullable(other.getContentType()).orElse(getContentType()));
        setCreateTime(Optional.ofNullable(other.getCreateTime()).orElse(getCreateTime()));
        setStartTime(Optional.ofNullable(other.getStartTime()).orElse(getStartTime()));
        setEndTime(Optional.ofNullable(other.getEndTime()).orElse(getEndTime()));
        setTodoRef(Optional.ofNullable(other.getTodoRef()).orElse(getTodoRef()));
        setContent(Optional.ofNullable(other.getContent()).orElse(getContent()));
        // account
        setAid(Optional.ofNullable(other.getAid()).orElse(getAid()));
        setLooked(Optional.ofNullable(other.getLooked()).orElse(getLooked()));
        setTop(Optional.ofNullable(other.getTop()).orElse(getTop()));
        setDel(Optional.ofNullable(other.getDel()).orElse(getDel()));
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
        setTodoRef(notice.getTodoRef());
        setVersion(notice.getVersion());
        return this;
    }

    public AccountNoticeInfo setAccountOperation(AccountNotice accountOperation) {
        if (accountOperation == null) {
            return this;
        }
        setAid(accountOperation.getAid());
        setId(accountOperation.getNid());
        setLooked(accountOperation.getLooked());
        setTop(accountOperation.getTop());
        setDel(accountOperation.getDel());
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

    public Boolean getLooked() {
        return looked;
    }

    public AccountNoticeInfo setLooked(Boolean read) {
        looked = read;
        return this;
    }

    public Boolean getTop() {
        return top;
    }

    public AccountNoticeInfo setTop(Boolean top) {
        this.top = top;
        return this;
    }

    public Boolean getDel() {
        return del;
    }

    public AccountNoticeInfo setDel(Boolean delete) {
        del = delete;
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

    public List<AccountTodoInfo> getTodoList() {
        return todoList == null ? new ArrayList<>() : todoList;
    }

    public AccountNoticeInfo setTodoList(List<AccountTodoInfo> todoList) {
        this.todoList = todoList;
        return this;
    }

    public List<NoticeInfo> getNoticeInfos() {
        return noticeInfos;
    }

    public AccountNoticeInfo setNoticeInfos(List<NoticeInfo> noticeInfos) {
        this.noticeInfos = noticeInfos;
        return this;
    }

    public List<String> getFiles() {
        return files;
    }

    public AccountNoticeInfo setFiles(List<String> files) {
        this.files = files;
        return this;
    }
}
