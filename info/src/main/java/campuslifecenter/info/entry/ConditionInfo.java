package campuslifecenter.info.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class ConditionInfo implements Serializable {
    @ApiModelProperty(value = "引用")
    private String ref;

    @ApiModelProperty(value = "信息 id")
    private Long iid;

    @ApiModelProperty(value = "值")
    private String text;

    @ApiModelProperty(value = "类型:0x00,通用;0x10数字")
    private Integer type;

    private Boolean dynamic;

    private static final long serialVersionUID = 1L;

    public String getRef() {
        return ref;
    }

    public ConditionInfo withRef(String ref) {
        this.setRef(ref);
        return this;
    }

    public void setRef(String ref) {
        this.ref = ref == null ? null : ref.trim();
    }

    public Long getIid() {
        return iid;
    }

    public ConditionInfo withIid(Long iid) {
        this.setIid(iid);
        return this;
    }

    public void setIid(Long iid) {
        this.iid = iid;
    }

    public String getText() {
        return text;
    }

    public ConditionInfo withText(String text) {
        this.setText(text);
        return this;
    }

    public void setText(String text) {
        this.text = text == null ? null : text.trim();
    }

    public Integer getType() {
        return type;
    }

    public ConditionInfo withType(Integer type) {
        this.setType(type);
        return this;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Boolean getDynamic() {
        return dynamic;
    }

    public ConditionInfo withDynamic(Boolean dynamic) {
        this.setDynamic(dynamic);
        return this;
    }

    public void setDynamic(Boolean dynamic) {
        this.dynamic = dynamic;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", ref=").append(ref);
        sb.append(", iid=").append(iid);
        sb.append(", text=").append(text);
        sb.append(", type=").append(type);
        sb.append(", dynamic=").append(dynamic);
        sb.append("]");
        return sb.toString();
    }
}