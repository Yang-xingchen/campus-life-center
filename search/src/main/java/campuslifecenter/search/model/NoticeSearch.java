package campuslifecenter.search.model;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Document(indexName = "notice")
public class NoticeSearch implements Serializable {

    @Id
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "创建者")
    private String creator;

    @ApiModelProperty(value = "发布组织id")
    private Integer organization;

    @ApiModelProperty(value = "状态: 0.删除; 1,创建; 2.待审核; 3.发布中; 4.发布完成")
    private String publishStatus;

    @ApiModelProperty(value = "可见性: 0,公开; 1,仅通知成员; 2,仅自己")
    private String visibility;

    @ApiModelProperty(value = "重要程度: 0,最低; 5,最高")
    private Integer importance;

    @ApiModelProperty(value = "版本，更新时自增")
    private Integer version;

    @ApiModelProperty(value = "标题")
    @Field(type = FieldType.Text, analyzer = "ik_smart")
    private String title;

    @ApiModelProperty(value = "正文文本格式类型: 0,纯文本; 1,Markdown; 2,HTML")
    private String contentType;

    @ApiModelProperty(value = "创建日期")
    private Date createTime;

    @ApiModelProperty(value = "通知类型: 0,消息; 1,事件; 2,活动")
    private String publicType;

    @ApiModelProperty(value = "type==0: null; type==1: 日期; type==2: 开始日期")
    private Date startTime;

    @ApiModelProperty(value = "type==0: null; type==1: null; type==2: 截止日期")
    private Date endTime;

    @ApiModelProperty(value = "引用")
    private String ref;

    @ApiModelProperty(value = "正文内容")
    @Field(type = FieldType.Text, analyzer = "ik_smart")
    private String content;

    @ApiModelProperty("创建者名")
    private String creatorName;

    @ApiModelProperty("组织名")
    private String organizationName;

    @ApiModelProperty("标签列表")
    private List<String> tag;

    @ApiModelProperty("待办列表")
    private List<String> todoList;

    // @ApiModelProperty("信息填写")
    // private List<AccountNoticeInfo.Info> noticeInfos;

    @ApiModelProperty("文件列表")
    private List<String> files;

    private static transient final String[] PUBLISH_STATUS_MAP = new String[]{
            "删除", "创建", "待审核", "发布中", "发布完成"
    };

    private static transient final String[] VISIBILITY_MAP = new String[] {
            "公开", "仅通知成员", "仅自己"
    };

    private static transient final String[] CONTENT_TYPE_MAP = new String[]{
            "纯文本", "Markdown", "HTML"
    };

    private static transient final String[] PUBLISH_TYPE_MAP = new String[]{
            "消息", "事件", "活动"
    };

    public static NoticeSearch createByInfo(NoticeInfo noticeInfo) {
        NoticeSearch search = new NoticeSearch();
        search.setId(noticeInfo.getId());
        search.setCreator(noticeInfo.getCreator());
        search.setOrganization(noticeInfo.getOrganization());
        search.setPublishStatus(PUBLISH_STATUS_MAP[Optional.ofNullable(noticeInfo.getPublishStatus()).orElse(4)]);
        search.setVisibility(VISIBILITY_MAP[noticeInfo.getVisibility()]);
        search.setImportance(noticeInfo.getImportance());
        search.setVersion(noticeInfo.getVersion());
        search.setTitle(noticeInfo.getTitle());
        search.setContentType(CONTENT_TYPE_MAP[noticeInfo.getContentType()]);
        search.setCreateTime(noticeInfo.getCreateTime());
        search.setPublicType(PUBLISH_TYPE_MAP[noticeInfo.getPublicType()]);
        search.setStartTime(noticeInfo.getStartTime());
        search.setEndTime(noticeInfo.getEndTime());
        search.setRef(noticeInfo.getRef());
        search.setContent(noticeInfo.getContent());
        search.setCreatorName(noticeInfo.getCreatorName());
        search.setOrganizationName(noticeInfo.getOrganizationName());
        search.setTag(noticeInfo.getTag());
        search.setTodoList(noticeInfo.getTodoList().stream().map(NoticeInfo.TodoInfo::getValue).collect(Collectors.toList()));
        search.setFiles(noticeInfo.getFiles());
        return search;
    };

    public Long getId() {
        return id;
    }

    public NoticeSearch setId(Long id) {
        this.id = id;
        return this;
    }

    public String getCreator() {
        return creator;
    }

    public NoticeSearch setCreator(String creator) {
        this.creator = creator;
        return this;
    }

    public Integer getOrganization() {
        return organization;
    }

    public NoticeSearch setOrganization(Integer organization) {
        this.organization = organization;
        return this;
    }

    public String getPublishStatus() {
        return publishStatus;
    }

    public NoticeSearch setPublishStatus(String publishStatus) {
        this.publishStatus = publishStatus;
        return this;
    }

    public String getVisibility() {
        return visibility;
    }

    public NoticeSearch setVisibility(String visibility) {
        this.visibility = visibility;
        return this;
    }

    public Integer getImportance() {
        return importance;
    }

    public NoticeSearch setImportance(Integer importance) {
        this.importance = importance;
        return this;
    }

    public Integer getVersion() {
        return version;
    }

    public NoticeSearch setVersion(Integer version) {
        this.version = version;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public NoticeSearch setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContentType() {
        return contentType;
    }

    public NoticeSearch setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public NoticeSearch setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getPublicType() {
        return publicType;
    }

    public NoticeSearch setPublicType(String publicType) {
        this.publicType = publicType;
        return this;
    }

    public Date getStartTime() {
        return startTime;
    }

    public NoticeSearch setStartTime(Date startTime) {
        this.startTime = startTime;
        return this;
    }

    public Date getEndTime() {
        return endTime;
    }

    public NoticeSearch setEndTime(Date endTime) {
        this.endTime = endTime;
        return this;
    }

    public String getRef() {
        return ref;
    }

    public NoticeSearch setRef(String ref) {
        this.ref = ref;
        return this;
    }

    public String getContent() {
        return content;
    }

    public NoticeSearch setContent(String content) {
        this.content = content;
        return this;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public NoticeSearch setCreatorName(String creatorName) {
        this.creatorName = creatorName;
        return this;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public NoticeSearch setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
        return this;
    }

    public List<String> getTag() {
        return tag;
    }

    public NoticeSearch setTag(List<String> tag) {
        this.tag = tag;
        return this;
    }

    public List<String> getTodoList() {
        return todoList;
    }

    public NoticeSearch setTodoList(List<String> todoList) {
        this.todoList = todoList;
        return this;
    }

    public List<String> getFiles() {
        return files;
    }

    public NoticeSearch setFiles(List<String> files) {
        this.files = files;
        return this;
    }
}
