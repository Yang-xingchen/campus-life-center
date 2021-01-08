package campuslifecenter.notice.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class NoticeUpdateLogKey implements Serializable {
    @ApiModelProperty(value = "id")
    private Long nid;

    private Integer version;

    private static final long serialVersionUID = 1L;

    public Long getNid() {
        return nid;
    }

    public NoticeUpdateLogKey withNid(Long nid) {
        this.setNid(nid);
        return this;
    }

    public void setNid(Long nid) {
        this.nid = nid;
    }

    public Integer getVersion() {
        return version;
    }

    public NoticeUpdateLogKey withVersion(Integer version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", nid=").append(nid);
        sb.append(", version=").append(version);
        sb.append("]");
        return sb.toString();
    }
}