package campuslifecenter.info.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class InfoFile implements Serializable {
    private Long id;

    private String path;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public InfoFile withId(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public InfoFile withPath(String path) {
        this.setPath(path);
        return this;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", path=").append(path);
        sb.append("]");
        return sb.toString();
    }
}