package campuslifecenter.notice.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class AccountNoticeTodoKey implements Serializable {
    @ApiModelProperty(value = "id")
    private Long nid;

    @ApiModelProperty(value = "todo id")
    private Integer id;

    private String aid;

    private static final long serialVersionUID = 1L;

    public Long getNid() {
        return nid;
    }

    public AccountNoticeTodoKey withNid(Long nid) {
        this.setNid(nid);
        return this;
    }

    public void setNid(Long nid) {
        this.nid = nid;
    }

    public Integer getId() {
        return id;
    }

    public AccountNoticeTodoKey withId(Integer id) {
        this.setId(id);
        return this;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAid() {
        return aid;
    }

    public AccountNoticeTodoKey withAid(String aid) {
        this.setAid(aid);
        return this;
    }

    public void setAid(String aid) {
        this.aid = aid == null ? null : aid.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", nid=").append(nid);
        sb.append(", id=").append(id);
        sb.append(", aid=").append(aid);
        sb.append("]");
        return sb.toString();
    }
}