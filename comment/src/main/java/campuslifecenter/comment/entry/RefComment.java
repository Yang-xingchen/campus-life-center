package campuslifecenter.comment.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class RefComment implements Serializable {
    private Long id;

    private String ref;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public RefComment withId(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRef() {
        return ref;
    }

    public RefComment withRef(String ref) {
        this.setRef(ref);
        return this;
    }

    public void setRef(String ref) {
        this.ref = ref == null ? null : ref.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", ref=").append(ref);
        sb.append("]");
        return sb.toString();
    }
}