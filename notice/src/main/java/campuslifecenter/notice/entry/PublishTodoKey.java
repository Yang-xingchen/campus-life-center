package campuslifecenter.notice.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class PublishTodoKey implements Serializable {
    @ApiModelProperty(value = "通知id")
    private Long nid;

    @ApiModelProperty(value = "todo id")
    private Long tid;

    private static final long serialVersionUID = 1L;

    public Long getNid() {
        return nid;
    }

    public PublishTodoKey withNid(Long nid) {
        this.setNid(nid);
        return this;
    }

    public void setNid(Long nid) {
        this.nid = nid;
    }

    public Long getTid() {
        return tid;
    }

    public PublishTodoKey withTid(Long tid) {
        this.setTid(tid);
        return this;
    }

    public void setTid(Long tid) {
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