package campuslifecenter.todo.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class Todo implements Serializable {
    @ApiModelProperty(value = "todo id")
    private Long id;

    @ApiModelProperty(value = "来源")
    private String ref;

    @ApiModelProperty(value = "内容")
    private String content;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public Todo withId(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRef() {
        return ref;
    }

    public Todo withRef(String ref) {
        this.setRef(ref);
        return this;
    }

    public void setRef(String ref) {
        this.ref = ref == null ? null : ref.trim();
    }

    public String getContent() {
        return content;
    }

    public Todo withContent(String content) {
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
        sb.append(", ref=").append(ref);
        sb.append(", content=").append(content);
        sb.append("]");
        return sb.toString();
    }
}