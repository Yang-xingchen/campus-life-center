package campuslifecenter.notice.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class AccountNoticeFinishButtonKey implements Serializable {
    @ApiModelProperty(value = "账户id")
    private String aid;

    private Long nbid;

    private static final long serialVersionUID = 1L;

    public String getAid() {
        return aid;
    }

    public AccountNoticeFinishButtonKey withAid(String aid) {
        this.setAid(aid);
        return this;
    }

    public void setAid(String aid) {
        this.aid = aid == null ? null : aid.trim();
    }

    public Long getNbid() {
        return nbid;
    }

    public AccountNoticeFinishButtonKey withNbid(Long nbid) {
        this.setNbid(nbid);
        return this;
    }

    public void setNbid(Long nbid) {
        this.nbid = nbid;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", aid=").append(aid);
        sb.append(", nbid=").append(nbid);
        sb.append("]");
        return sb.toString();
    }
}