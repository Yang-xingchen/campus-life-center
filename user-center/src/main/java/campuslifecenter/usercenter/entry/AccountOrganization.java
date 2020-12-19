package campuslifecenter.usercenter.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class AccountOrganization extends AccountOrganizationKey implements Serializable {
    @ApiModelProperty(value = "角色")
    private Byte role;

    @ApiModelProperty(value = "角色名")
    private String roleName;

    private static final long serialVersionUID = 1L;

    public Byte getRole() {
        return role;
    }

    public AccountOrganization withRole(Byte role) {
        this.setRole(role);
        return this;
    }

    public void setRole(Byte role) {
        this.role = role;
    }

    public String getRoleName() {
        return roleName;
    }

    public AccountOrganization withRoleName(String roleName) {
        this.setRoleName(roleName);
        return this;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", role=").append(role);
        sb.append(", roleName=").append(roleName);
        sb.append("]");
        return sb.toString();
    }
}