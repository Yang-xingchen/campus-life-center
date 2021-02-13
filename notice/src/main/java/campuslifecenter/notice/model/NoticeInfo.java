package campuslifecenter.notice.model;

import campuslifecenter.notice.entry.Notice;
import campuslifecenter.notice.entry.NoticeTagKey;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NoticeInfo extends Notice {

    @ApiModelProperty("创建者名")
    private String creatorName;

    @ApiModelProperty("组织名")
    private String organizationName;

    @ApiModelProperty("标签列表")
    private List<String> tag;

    @ApiModelProperty("待办列表")
    private List<AccountTodoInfo> todoList;

    @ApiModelProperty("信息填写")
    private List<AccountNoticeInfo.Info> noticeInfos;

    @ApiModelProperty("文件列表")
    private List<String> files;

    public NoticeInfo setNotice(Notice notice) {
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
        setContentType(notice.getContentType());
        setRef(notice.getRef());
        setVersion(notice.getVersion());
        return this;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public NoticeInfo setCreatorName(String creatorName) {
        this.creatorName = creatorName;
        return this;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public NoticeInfo setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
        return this;
    }

    public NoticeInfo withNoticeTag(List<NoticeTagKey> tag) {
        this.tag = tag.stream().map(NoticeTagKey::getTag).collect(Collectors.toList());
        return this;
    }

    public List<String> getTag() {
        return tag == null ? new ArrayList<>() : tag;
    }

    public NoticeInfo setTag(List<String> tag) {
        this.tag = tag;
        return this;
    }

    public List<AccountTodoInfo> getTodoList() {
        return todoList == null ? new ArrayList<>() : todoList;
    }

    public NoticeInfo setTodoList(List<AccountTodoInfo> todoList) {
        this.todoList = todoList;
        return this;
    }

    public List<AccountNoticeInfo.Info> getNoticeInfos() {
        return noticeInfos;
    }

    public NoticeInfo setNoticeInfos(List<AccountNoticeInfo.Info> noticeInfos) {
        this.noticeInfos = noticeInfos;
        return this;
    }

    public List<String> getFiles() {
        return files;
    }

    public NoticeInfo setFiles(List<String> files) {
        this.files = files;
        return this;
    }

}
