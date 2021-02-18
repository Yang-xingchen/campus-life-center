package campuslifecenter.usercenter.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class AccountOrganizationRoleKey implements Serializable {
    @ApiModelProperty(value = "账户id")
    private String aid;

    @ApiModelProperty(value = "组织id")
    private Integer oid;

    @ApiModelProperty(value = "角色id")
    private Integer id;

    private static final long serialVersionUID = 1L;

    public String getAid() {
        return aid;
    }

    public AccountOrganizationRoleKey withAid(String aid) {
        this.setAid(aid);
        return this;
    }

    public void setAid(String aid) {
        this.aid = aid == null ? null : aid.trim();
    }

    public Integer getOid() {
        return oid;
    }

    public AccountOrganizationRoleKey withOid(Integer oid) {
        this.setOid(oid);
        return this;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public Integer getId() {
        return id;
    }

    public AccountOrganizationRoleKey withId(Integer id) {
        this.setId(id);
        return this;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", aid=").append(aid);
        sb.append(", oid=").append(oid);
        sb.append(", id=").append(id);
        sb.append("]");
        return sb.toString();
    }
}