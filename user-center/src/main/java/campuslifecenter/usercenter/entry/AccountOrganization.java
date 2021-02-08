package campuslifecenter.usercenter.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class AccountOrganization extends AccountOrganizationKey implements Serializable {
    @ApiModelProperty(value = "是否隐藏")
    private Boolean hide;

    @ApiModelProperty(value = "账户是否同意")
    private Boolean accountAccept;

    @ApiModelProperty(value = "组织是否同意")
    private Boolean organizationAccept;

    private static final long serialVersionUID = 1L;

    public Boolean getHide() {
        return hide;
    }

    public AccountOrganization withHide(Boolean hide) {
        this.setHide(hide);
        return this;
    }

    public void setHide(Boolean hide) {
        this.hide = hide;
    }

    public Boolean getAccountAccept() {
        return accountAccept;
    }

    public AccountOrganization withAccountAccept(Boolean accountAccept) {
        this.setAccountAccept(accountAccept);
        return this;
    }

    public void setAccountAccept(Boolean accountAccept) {
        this.accountAccept = accountAccept;
    }

    public Boolean getOrganizationAccept() {
        return organizationAccept;
    }

    public AccountOrganization withOrganizationAccept(Boolean organizationAccept) {
        this.setOrganizationAccept(organizationAccept);
        return this;
    }

    public void setOrganizationAccept(Boolean organizationAccept) {
        this.organizationAccept = organizationAccept;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", hide=").append(hide);
        sb.append(", accountAccept=").append(accountAccept);
        sb.append(", organizationAccept=").append(organizationAccept);
        sb.append("]");
        sb.append(", from super class ");
        sb.append(super.toString());
        return sb.toString();
    }
}