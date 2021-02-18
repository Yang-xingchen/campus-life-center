package campuslifecenter.todo.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class RefTodoKey implements Serializable {
    @ApiModelProperty(value = "引用")
    private String ref;

    @ApiModelProperty(value = "todo id")
    private Long id;

    private static final long serialVersionUID = 1L;

    public String getRef() {
        return ref;
    }

    public RefTodoKey withRef(String ref) {
        this.setRef(ref);
        return this;
    }

    public void setRef(String ref) {
        this.ref = ref == null ? null : ref.trim();
    }

    public Long getId() {
        return id;
    }

    public RefTodoKey withId(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", ref=").append(ref);
        sb.append(", id=").append(id);
        sb.append("]");
        return sb.toString();
    }
}