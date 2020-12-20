package campuslifecenter.notice.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class AccountNoticeKey implements Serializable {
    @ApiModelProperty(value = "id")
    private Integer nid;

    @ApiModelProperty(value = "id")
    private String aid;

    private static final long serialVersionUID = 1L;

    public Integer getNid() {
        return nid;
    }

    public AccountNoticeKey withNid(Integer nid) {
        this.setNid(nid);
        return this;
    }

    public void setNid(Integer nid) {
        this.nid = nid;
    }

    public String getAid() {
        return aid;
    }

    public AccountNoticeKey withAid(String aid) {
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
        sb.append(", aid=").append(aid);
        sb.append("]");
        return sb.toString();
    }
}