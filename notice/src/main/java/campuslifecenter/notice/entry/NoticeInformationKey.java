package campuslifecenter.notice.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class NoticeInformationKey implements Serializable {
    @ApiModelProperty(value = "id")
    private Integer nid;

    private Integer iid;

    private static final long serialVersionUID = 1L;

    public Integer getNid() {
        return nid;
    }

    public NoticeInformationKey withNid(Integer nid) {
        this.setNid(nid);
        return this;
    }

    public void setNid(Integer nid) {
        this.nid = nid;
    }

    public Integer getIid() {
        return iid;
    }

    public NoticeInformationKey withIid(Integer iid) {
        this.setIid(iid);
        return this;
    }

    public void setIid(Integer iid) {
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