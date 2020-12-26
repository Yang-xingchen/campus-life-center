package campuslifecenter.notice.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class Notice implements Serializable {
    @ApiModelProperty(value = "id")
    private Long id;

    private String creator;

    @ApiModelProperty(value = "id")
    private Integer organization;

    @ApiModelProperty(value = ": 0,; 1,")
    private Boolean visibility;

    @ApiModelProperty(value = ": 0,; 5,")
    private Integer importance;

    @ApiModelProperty(value = ": 0,; 1,; 2.")
    private Integer publicType;

    private String title;

    private Date createTime;

    @ApiModelProperty(value = "type==0: null; type==1: ; type==2: ")
    private Date startTime;

    @ApiModelProperty(value = "type==0: null; type==1: null; type==2: ")
    private Date endTime;

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
        sb.append(", title=").append(title);
        sb.append(", createTime=").append(createTime);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", content=").append(content);
        sb.append("]");
        return sb.toString();
    }
}