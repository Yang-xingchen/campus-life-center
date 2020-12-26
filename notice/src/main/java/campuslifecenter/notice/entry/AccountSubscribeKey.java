package campuslifecenter.notice.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class AccountSubscribeKey implements Serializable {
    @ApiModelProperty(value = "id")
    private String aid;

    @ApiModelProperty(value = "id")
    private Integer oid;

    private static final long serialVersionUID = 1L;

    public String getAid() {
        return aid;
    }

    public AccountSubscribeKey withAid(String aid) {
        this.setAid(aid);
        return this;
    }

    public void setAid(String aid) {
        this.aid = aid == null ? null : aid.trim();
    }

    public Integer getOid() {
        return oid;
    }

    public AccountSubscribeKey withOid(Integer oid) {
        this.setOid(oid);
        return this;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", aid=").append(aid);
        sb.append(", oid=").append(oid);
        sb.append("]");
        return sb.toString();
    }
}