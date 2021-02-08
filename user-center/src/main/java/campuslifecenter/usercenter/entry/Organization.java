package campuslifecenter.usercenter.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class Organization implements Serializable {
    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "父id")
    private Integer parent;

    @ApiModelProperty(value = "是否隐藏")
    private Boolean hide;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "创建者")
    private String creator;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "可见性")
    private Integer visibility;

    @ApiModelProperty(value = "创建日期")
    private Date createData;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public Organization withId(Integer id) {
        this.setId(id);
        return this;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParent() {
        return parent;
    }

    public Organization withParent(Integer parent) {
        this.setParent(parent);
        return this;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public Boolean getHide() {
        return hide;
    }

    public Organization withHide(Boolean hide) {
        this.setHide(hide);
        return this;
    }

    public void setHide(Boolean hide) {
        this.hide = hide;
    }

    public String getType() {
        return type;
    }

    public Organization withType(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getCreator() {
        return creator;
    }

    public Organization withCreator(String creator) {
        this.setCreator(creator);
        return this;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getName() {
        return name;
    }

    public Organization withName(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getVisibility() {
        return visibility;
    }

    public Organization withVisibility(Integer visibility) {
        this.setVisibility(visibility);
        return this;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    public Date getCreateData() {
        return createData;
    }

    public Organization withCreateData(Date createData) {
        this.setCreateData(createData);
        return this;
    }

    public void setCreateData(Date createData) {
        this.createData = createData;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", parent=").append(parent);
        sb.append(", hide=").append(hide);
        sb.append(", type=").append(type);
        sb.append(", creator=").append(creator);
        sb.append(", name=").append(name);
        sb.append(", visibility=").append(visibility);
        sb.append(", createData=").append(createData);
        sb.append("]");
        return sb.toString();
    }
}