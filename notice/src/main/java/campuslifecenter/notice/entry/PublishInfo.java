package campuslifecenter.notice.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class PublishInfo extends PublishInfoKey implements Serializable {
    @ApiModelProperty(value = "是否动态")
    private Boolean dynamic;

    @ApiModelProperty(value = "值")
    private String text;

    @ApiModelProperty(value = "类型:0x00,通用;0x10数字")
    private Integer type;

    private static final long serialVersionUID = 1L;

    public Boolean getDynamic() {
        return dynamic;
    }

    public PublishInfo withDynamic(Boolean dynamic) {
        this.setDynamic(dynamic);
        return this;
    }

    public void setDynamic(Boolean dynamic) {
        this.dynamic = dynamic;
    }

    public String getText() {
        return text;
    }

    public PublishInfo withText(String text) {
        this.setText(text);
        return this;
    }

    public void setText(String text) {
        this.text = text == null ? null : text.trim();
    }

    public Integer getType() {
        return type;
    }

    public PublishInfo withType(Integer type) {
        this.setType(type);
        return this;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", dynamic=").append(dynamic);
        sb.append(", text=").append(text);
        sb.append(", type=").append(type);
        sb.append("]");
        sb.append(", from super class ");
        sb.append(super.toString());
        return sb.toString();
    }
}