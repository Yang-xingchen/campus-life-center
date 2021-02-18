package campuslifecenter.todo.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class RefTodo extends RefTodoKey implements Serializable {
    @ApiModelProperty(value = "来源类型: 0.用户; 1.通知")
    private Integer type;

    private static final long serialVersionUID = 1L;

    public Integer getType() {
        return type;
    }

    public RefTodo withType(Integer type) {
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
        sb.append(", type=").append(type);
        sb.append("]");
        sb.append(", from super class ");
        sb.append(super.toString());
        return sb.toString();
    }
}