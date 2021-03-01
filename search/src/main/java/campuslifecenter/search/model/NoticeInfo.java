package campuslifecenter.search.model;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class NoticeInfo implements Serializable {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "创建者")
    private String creator;

    @ApiModelProperty(value = "发布组织id")
    private Integer organization;

    @ApiModelProperty(value = "状态: 0.删除; 1,创建; 2.待审核; 3.发布中; 4.发布完成")
    private Integer publishStatus;

    @ApiModelProperty(value = "可见性: 0,公开; 1,仅通知成员; 2,仅自己")
    private Integer visibility;

    @ApiModelProperty(value = "重要程度: 0,最低; 5,最高")
    private Integer importance;

    @ApiModelProperty(value = "版本，更新时自增")
    private Integer version;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "正文文本格式类型: 0,纯文本; 1,Markdown; 2,HTML")
    private Integer contentType;

    @ApiModelProperty(value = "创建日期")
    private Date createTime;

    @ApiModelProperty(value = "通知类型: 0,消息; 1,事件; 2,活动")
    private Integer publicType;

    @ApiModelProperty(value = "type==0: null; type==1: 日期; type==2: 开始日期")
    private Date startTime;

    @ApiModelProperty(value = "type==0: null; type==1: null; type==2: 截止日期")
    private Date endTime;

    @ApiModelProperty(value = "引用")
    private String ref;

    @ApiModelProperty(value = "正文内容")
    private String content;

    @ApiModelProperty("创建者名")
    private String creatorName;

    @ApiModelProperty("组织名")
    private String organizationName;

    @ApiModelProperty("标签列表")
    private List<String> tag;

    @ApiModelProperty("待办列表")
    private List<TodoInfo> todoList;

    // @ApiModelProperty("信息填写")

    // private List<AccountNoticeInfo.Info> noticeInfos;
    @ApiModelProperty("文件列表")
    private List<String> files;

    public static class TodoInfo implements Serializable{

        private String value;

        public String getValue() {
            return value;
        }

        public TodoInfo setValue(String value) {
            this.value = value;
            return this;
        }
    }

    public Long getId() {
        return id;
    }

    public NoticeInfo setId(Long id) {
        this.id = id;
        return this;
    }

    public String getCreator() {
        return creator;
    }

    public NoticeInfo setCreator(String creator) {
        this.creator = creator;
        return this;
    }

    public Integer getOrganization() {
        return organization;
    }

    public NoticeInfo setOrganization(Integer organization) {
        this.organization = organization;
        return this;
    }

    public Integer getPublishStatus() {
        return publishStatus;
    }

    public NoticeInfo setPublishStatus(Integer publishStatus) {
        this.publishStatus = publishStatus;
        return this;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public NoticeInfo setVisibility(Integer visibility) {
        this.visibility = visibility;
        return this;
    }

    public Integer getImportance() {
        return importance;
    }

    public NoticeInfo setImportance(Integer importance) {
        this.importance = importance;
        return this;
    }

    public Integer getVersion() {
        return version;
    }

    public NoticeInfo setVersion(Integer version) {
        this.version = version;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public NoticeInfo setTitle(String title) {
        this.title = title;
        return this;
    }

    public Integer getContentType() {
        return contentType;
    }

    public NoticeInfo setContentType(Integer contentType) {
        this.contentType = contentType;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public NoticeInfo setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getPublicType() {
        return publicType;
    }

    public NoticeInfo setPublicType(Integer publicType) {
        this.publicType = publicType;
        return this;
    }

    public Date getStartTime() {
        return startTime;
    }

    public NoticeInfo setStartTime(Date startTime) {
        this.startTime = startTime;
        return this;
    }

    public Date getEndTime() {
        return endTime;
    }

    public NoticeInfo setEndTime(Date endTime) {
        this.endTime = endTime;
        return this;
    }

    public String getRef() {
        return ref;
    }

    public NoticeInfo setRef(String ref) {
        this.ref = ref;
        return this;
    }

    public String getContent() {
        return content;
    }

    public NoticeInfo setContent(String content) {
        this.content = content;
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

    public List<String> getTag() {
        return tag;
    }

    public NoticeInfo setTag(List<String> tag) {
        this.tag = tag;
        return this;
    }

    public List<TodoInfo> getTodoList() {
        return todoList;
    }

    public NoticeInfo setTodoList(List<TodoInfo> todoList) {
        this.todoList = todoList;
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
