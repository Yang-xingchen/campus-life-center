package campuslifecenter.todo.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class Todo implements Serializable {
    @ApiModelProperty(value = "todo id")
    private Long id;

    @ApiModelProperty(value = "来源")
    private String source;

    @ApiModelProperty(value = "值")
    private String title;

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

    public String getSource() {
        return source;
    }

    public Todo withSource(String source) {
        this.setSource(source);
        return this;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public String getTitle() {
        return title;
    }

    public Todo withTitle(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", source=").append(source);
        sb.append(", title=").append(title);
        sb.append("]");
        return sb.toString();
    }
}