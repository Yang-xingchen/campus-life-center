package campuslifecenter.notice.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class PublishRefKey implements Serializable {
    @ApiModelProperty(value = "通知id")
    private Long nid;

    @ApiModelProperty(value = "类型: 1.TODO; 2.INFO")
    private Integer type;

    @ApiModelProperty(value = "引用")
    private String ref;

    private static final long serialVersionUID = 1L;

    public Long getNid() {
        return nid;
    }

    public PublishRefKey withNid(Long nid) {
        this.setNid(nid);
        return this;
    }

    public void setNid(Long nid) {
        this.nid = nid;
    }

    public Integer getType() {
        return type;
    }

    public PublishRefKey withType(Integer type) {
        this.setType(type);
        return this;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getRef() {
        return ref;
    }

    public PublishRefKey withRef(String ref) {
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
        sb.append(", nid=").append(nid);
        sb.append(", type=").append(type);
        sb.append(", ref=").append(ref);
        sb.append("]");
        return sb.toString();
    }
}