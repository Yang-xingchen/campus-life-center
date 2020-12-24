package campuslifecenter.notice.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class NoticeTodoKey implements Serializable {
    @ApiModelProperty(value = "todo id")
    private Integer id;

    @ApiModelProperty(value = "通知id")
    private Long nid;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public NoticeTodoKey withId(Integer id) {
        this.setId(id);
        return this;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getNid() {
        return nid;
    }

    public NoticeTodoKey withNid(Long nid) {
        this.setNid(nid);
        return this;
    }

    public void setNid(Long nid) {
        this.nid = nid;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", nid=").append(nid);
        sb.append("]");
        return sb.toString();
    }
}