package campuslifecenter.notice.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class DynamicInfoObserveKey implements Serializable {
    private Long nid;

    private Integer tid;

    private Long iid;

    private static final long serialVersionUID = 1L;

    public Long getNid() {
        return nid;
    }

    public DynamicInfoObserveKey withNid(Long nid) {
        this.setNid(nid);
        return this;
    }

    public void setNid(Long nid) {
        this.nid = nid;
    }

    public Integer getTid() {
        return tid;
    }

    public DynamicInfoObserveKey withTid(Integer tid) {
        this.setTid(tid);
        return this;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Long getIid() {
        return iid;
    }

    public DynamicInfoObserveKey withIid(Long iid) {
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
        sb.append(", tid=").append(tid);
        sb.append(", iid=").append(iid);
        sb.append("]");
        return sb.toString();
    }
}