package campuslifecenter.usercenter.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class AccountOrganizationKey implements Serializable {
    @ApiModelProperty(value = "账户id")
    private String aid;

    @ApiModelProperty(value = "组织id")
    private Integer oid;

    @ApiModelProperty(value = "角色")
    private Integer role;

    private static final long serialVersionUID = 1L;

    public String getAid() {
        return aid;
    }

    public AccountOrganizationKey withAid(String aid) {
        this.setAid(aid);
        return this;
    }

    public void setAid(String aid) {
        this.aid = aid == null ? null : aid.trim();
    }

    public Integer getOid() {
        return oid;
    }

    public AccountOrganizationKey withOid(Integer oid) {
        this.setOid(oid);
        return this;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public Integer getRole() {
        return role;
    }

    public AccountOrganizationKey withRole(Integer role) {
        this.setRole(role);
        return this;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", aid=").append(aid);
        sb.append(", oid=").append(oid);
        sb.append(", role=").append(role);
        sb.append("]");
        return sb.toString();
    }
}