package campuslifecenter.notice.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class DynamicTodoObserveKey implements Serializable {
    private Long nid;

    private Integer tid;

    private static final long serialVersionUID = 1L;

    public Long getNid() {
        return nid;
    }

    public DynamicTodoObserveKey withNid(Long nid) {
        this.setNid(nid);
        return this;
    }

    public void setNid(Long nid) {
        this.nid = nid;
    }

    public Integer getTid() {
        return tid;
    }

    public DynamicTodoObserveKey withTid(Integer tid) {
        this.setTid(tid);
        return this;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", nid=").append(nid);
        sb.append(", tid=").append(tid);
        sb.append("]");
        return sb.toString();
    }
}