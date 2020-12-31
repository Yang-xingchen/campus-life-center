package campuslifecenter.notice.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class NoticeTodo extends NoticeTodoKey implements Serializable {
    @ApiModelProperty(value = ": 0, , value; 1, ")
    private Integer type;

    @ApiModelProperty(value = "id")
    private String typeValue;

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

    public String getTypeValue() {
        return typeValue;
    }

    public NoticeTodo withTypeValue(String typeValue) {
        this.setTypeValue(typeValue);
        return this;
    }

    public void setTypeValue(String typeValue) {
        this.typeValue = typeValue == null ? null : typeValue.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", type=").append(type);
        sb.append(", typeValue=").append(typeValue);
        sb.append("]");
        return sb.toString();
    }
}