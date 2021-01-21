package campuslifecenter.notice.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class Notice implements Serializable {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "创建者")
    private String creator;

    @ApiModelProperty(value = "组织id")
    private Integer organization;

    @ApiModelProperty(value = "可见性: 0,公开; 1,私有")
    private Boolean visibility;

    @ApiModelProperty(value = "重要程度: 0,最低; 5,最高")
    private Integer importance;

    @ApiModelProperty(value = "通知类型: 0,消息; 1,事件; 2.活动")
    private Integer publicType;

    @ApiModelProperty(value = "版本，更新时自增")
    private Integer version;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "正文文本格式类型: 0,纯文本; 1,Markdown; 2,HTML")
    private Integer contentType;

    @ApiModelProperty(value = "创建日期")
    private Date createTime;

    @ApiModelProperty(value = "type==0: null; type==1: 日期; type==2: 开始日期")
    private Date startTime;

    @ApiModelProperty(value = "type==0: null; type==1: null; type==2: 截止日期")
    private Date endTime;

    @ApiModelProperty(value = "todo 引用")
    private String todoRef;

    @ApiModelProperty(value = "文件引用路径")
    private String fileRef;

    @ApiModelProperty(value = "正文内容")
    private String content;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public Notice withId(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreator() {
        return creator;
    }

    public Notice withCreator(String creator) {
        this.setCreator(creator);
        return this;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Integer getOrganization() {
        return organization;
    }

    public Notice withOrganization(Integer organization) {
        this.setOrganization(organization);
        return this;
    }

    public void setOrganization(Integer organization) {
        this.organization = organization;
    }

    public Boolean getVisibility() {
        return visibility;
    }

    public Notice withVisibility(Boolean visibility) {
        this.setVisibility(visibility);
        return this;
    }

    public void setVisibility(Boolean visibility) {
        this.visibility = visibility;
    }

    public Integer getImportance() {
        return importance;
    }

    public Notice withImportance(Integer importance) {
        this.setImportance(importance);
        return this;
    }

    public void setImportance(Integer importance) {
        this.importance = importance;
    }

    public Integer getPublicType() {
        return publicType;
    }

    public Notice withPublicType(Integer publicType) {
        this.setPublicType(publicType);
        return this;
    }

    public void setPublicType(Integer publicType) {
        this.publicType = publicType;
    }

    public Integer getVersion() {
        return version;
    }

    public Notice withVersion(Integer version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getTitle() {
        return title;
    }

    public Notice withTitle(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getContentType() {
        return contentType;
    }

    public Notice withContentType(Integer contentType) {
        this.setContentType(contentType);
        return this;
    }

    public void setContentType(Integer contentType) {
        this.contentType = contentType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Notice withCreateTime(Date createTime) {
        this.setCreateTime(createTime);
        return this;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Notice withStartTime(Date startTime) {
        this.setStartTime(startTime);
        return this;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public Notice withEndTime(Date endTime) {
        this.setEndTime(endTime);
        return this;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getTodoRef() {
        return todoRef;
    }

    public Notice withTodoRef(String todoRef) {
        this.setTodoRef(todoRef);
        return this;
    }

    public void setTodoRef(String todoRef) {
        this.todoRef = todoRef == null ? null : todoRef.trim();
    }

    public String getFileRef() {
        return fileRef;
    }

    public Notice withFileRef(String fileRef) {
        this.setFileRef(fileRef);
        return this;
    }

    public void setFileRef(String fileRef) {
        this.fileRef = fileRef == null ? null : fileRef.trim();
    }

    public String getContent() {
        return content;
    }

    public Notice withContent(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", creator=").append(creator);
        sb.append(", organization=").append(organization);
        sb.append(", visibility=").append(visibility);
        sb.append(", importance=").append(importance);
        sb.append(", publicType=").append(publicType);
        sb.append(", version=").append(version);
        sb.append(", title=").append(title);
        sb.append(", contentType=").append(contentType);
        sb.append(", createTime=").append(createTime);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", todoRef=").append(todoRef);
        sb.append(", fileRef=").append(fileRef);
        sb.append(", content=").append(content);
        sb.append("]");
        return sb.toString();
    }
}