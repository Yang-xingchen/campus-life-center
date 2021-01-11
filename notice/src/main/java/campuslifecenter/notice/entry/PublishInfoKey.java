package campuslifecenter.notice.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class PublishInfoKey implements Serializable {
    @ApiModelProperty(value = "通知 id")
    private Long nid;

    @ApiModelProperty(value = "信息 id")
    private Long iid;

    private static final long serialVersionUID = 1L;

    public Long getNid() {
        return nid;
    }

    public PublishInfoKey withNid(Long nid) {
        this.setNid(nid);
        return this;
    }

    public void setNid(Long nid) {
        this.nid = nid;
    }

    public Long getIid() {
        return iid;
    }

    public PublishInfoKey withIid(Long iid) {
        this.setIid(iid);
        return this;
    }

    public void setIid(Long iid) {
        this.iid = iid;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", nid=").append(nid);
        sb.append(", iid=").append(iid);
        sb.append("]");
        return sb.toString();
    }
}