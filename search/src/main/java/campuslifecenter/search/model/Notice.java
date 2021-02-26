package campuslifecenter.search.model;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Document(indexName = "notice")
public class Notice implements Serializable {

    @Id
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
    @Field(type = FieldType.Text)
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

    public Long getId() {
        return id;
    }

    public Notice setId(Long id) {
        this.id = id;
        return this;
    }

    public String getCreator() {
        return creator;
    }

    public Notice setCreator(String creator) {
        this.creator = creator;
        return this;
    }

    public Integer getOrganization() {
        return organization;
    }

    public Notice setOrganization(Integer organization) {
        this.organization = organization;
        return this;
    }

    public Integer getPublishStatus() {
        return publishStatus;
    }

    public Notice setPublishStatus(Integer publishStatus) {
        this.publishStatus = publishStatus;
        return this;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public Notice setVisibility(Integer visibility) {
        this.visibility = visibility;
        return this;
    }

    public Integer getImportance() {
        return importance;
    }

    public Notice setImportance(Integer importance) {
        this.importance = importance;
        return this;
    }

    public Integer getVersion() {
        return version;
    }

    public Notice setVersion(Integer version) {
        this.version = version;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Notice setTitle(String title) {
        this.title = title;
        return this;
    }

    public Integer getContentType() {
        return contentType;
    }

    public Notice setContentType(Integer contentType) {
        this.contentType = contentType;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Notice setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getPublicType() {
        return publicType;
    }

    public Notice setPublicType(Integer publicType) {
        this.publicType = publicType;
        return this;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Notice setStartTime(Date startTime) {
        this.startTime = startTime;
        return this;
    }

    public Date getEndTime() {
        return endTime;
    }

    public Notice setEndTime(Date endTime) {
        this.endTime = endTime;
        return this;
    }

    public String getRef() {
        return ref;
    }

    public Notice setRef(String ref) {
        this.ref = ref;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Notice setContent(String content) {
        this.content = content;
        return this;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public Notice setCreatorName(String creatorName) {
        this.creatorName = creatorName;
        return this;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public Notice setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
        return this;
    }

    public List<String> getTag() {
        return tag;
    }

    public Notice setTag(List<String> tag) {
        this.tag = tag;
        return this;
    }

    public List<String> getTodoList() {
        return todoList;
    }

    public Notice setTodoList(List<String> todoList) {
        this.todoList = todoList;
        return this;
    }

    public List<String> getFiles() {
        return files;
    }

    public Notice setFiles(List<String> files) {
        this.files = files;
        return this;
    }
}
