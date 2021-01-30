package campuslifecenter.info.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class RefRoot implements Serializable {
    @ApiModelProperty(value = "引用，区分不同提交")
    private String ref;

    @ApiModelProperty(value = "根信息id，区分不同来源")
    private Long root;

    private static final long serialVersionUID = 1L;

    public String getRef() {
        return ref;
    }

    public RefRoot withRef(String ref) {
        this.setRef(ref);
        return this;
    }

    public void setRef(String ref) {
        this.ref = ref == null ? null : ref.trim();
    }

    public Long getRoot() {
        return root;
    }

    public RefRoot withRoot(Long root) {
        this.setRoot(root);
        return this;
    }

    public void setRoot(Long root) {
        this.root = root;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", ref=").append(ref);
        sb.append(", root=").append(root);
        sb.append("]");
        return sb.toString();
    }
}