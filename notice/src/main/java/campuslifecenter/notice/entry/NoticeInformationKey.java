package campuslifecenter.notice.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class NoticeInformationKey implements Serializable {
    @ApiModelProperty(value = "通知id")
    private Long nid;

    private Long iid;

    private static final long serialVersionUID = 1L;

    public Long getNid() {
        return nid;
    }

    public NoticeInformationKey withNid(Long nid) {
        this.setNid(nid);
        return this;
    }

    public void setNid(Long nid) {
        this.nid = nid;
    }

    public Long getIid() {
        return iid;
    }

    public NoticeInformationKey withIid(Long iid) {
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