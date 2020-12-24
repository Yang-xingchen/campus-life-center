package campuslifecenter.notice.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class NoticeTodo extends NoticeTodoKey implements Serializable {
    @ApiModelProperty(value = "类型: 0, 简单值, 见value字段; 1, 收集信息")
    private Integer type;

    @ApiModelProperty(value = "简单值的值")
    private String value;

    private static final long serialVersionUID = 1L;

    public Integer getType() {
        return type;
    }

    public NoticeTodo withType(Integer type) {
        this.setType(type);
        return this;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public NoticeTodo withValue(String value) {
        this.setValue(value);
        return this;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", type=").append(type);
        sb.append(", value=").append(value);
        sb.append("]");
        return sb.toString();
    }
}