package campuslifecenter.notice.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class Notice implements Serializable {
    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "创建者")
    private String creator;

    @ApiModelProperty(value = "组织id")
    private Integer organization;

    @ApiModelProperty(value = "可见性: 0:公开; 1:仅通知账户")
    private Byte visibility;

    @ApiModelProperty(value = "发布类型: 0:组织内成员; 1:订阅列表(见account_subscribe); 2: 静态名单列表(account_notice); 3: 动态条件(见notice_condition)")
    private Byte publicType;

    @ApiModelProperty(value = "创建日期")
    private Date createTime;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "重要程度: 0:最低; 5:最高")
    private Byte importance;

    @ApiModelProperty(value = "通知日期")
    private Date time;

    @ApiModelProperty(value = "正文内容")
    private String content;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public Notice withId(Integer id) {
        this.setId(id);
        return this;
    }

    public void setId(Integer id) {
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

    public Byte getVisibility() {
        return visibility;
    }

    public Notice withVisibility(Byte visibility) {
        this.setVisibility(visibility);
        return this;
    }

    public void setVisibility(Byte visibility) {
        this.visibility = visibility;
    }

    public Byte getPublicType() {
        return publicType;
    }

    public Notice withPublicType(Byte publicType) {
        this.setPublicType(publicType);
        return this;
    }

    public void setPublicType(Byte publicType) {
        this.publicType = publicType;
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

    public Byte getImportance() {
        return importance;
    }

    public Notice withImportance(Byte importance) {
        this.setImportance(importance);
        return this;
    }

    public void setImportance(Byte importance) {
        this.importance = importance;
    }

    public Date getTime() {
        return time;
    }

    public Notice withTime(Date time) {
        this.setTime(time);
        return this;
    }

    public void setTime(Date time) {
        this.time = time;
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
        sb.append(", publicType=").append(publicType);
        sb.append(", createTime=").append(createTime);
        sb.append(", title=").append(title);
        sb.append(", importance=").append(importance);
        sb.append(", time=").append(time);
        sb.append(", content=").append(content);
        sb.append("]");
        return sb.toString();
    }
}