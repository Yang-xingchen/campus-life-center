package campuslifecenter.usercenter.model;

import campuslifecenter.usercenter.entry.AccountOrganization;
import campuslifecenter.usercenter.entry.Organization;

import java.io.Serializable;
import java.util.Objects;

public class AccountOrganizationInfo implements Serializable {

    private String aid;
    private Integer oid;
    private String organizationName;
    private String organizationType;
    private int organizationVisibility;
    private int role;
    private String roleName;

    public static AccountOrganizationInfo createByAccountOrganization(AccountOrganization organization) {
        return new AccountOrganizationInfo()
                .withOrganization(organization);
    }

    public AccountOrganizationInfo withOrganization(AccountOrganization organization) {
        return setAid(organization.getAid())
                .setOid(organization.getOid())
                .setRole(organization.getRole())
                .setRoleName(organization.getRoleName());
    }

    public AccountOrganizationInfo withOrganization(Organization organization) {
        return setOid(organization.getId())
                .setOrganizationName(organization.getName())
                .setOrganizationVisibility(organization.getVisibility())
                .setOrganizationType(organization.getType());
    }

    public String getAid() {
        return aid;
    }

    public AccountOrganizationInfo setAid(String aid) {
        this.aid = aid;
        return this;
    }

    public Integer getOid() {
        return oid;
    }

    public AccountOrganizationInfo setOid(int oid) {
        this.oid = oid;
        return this;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public AccountOrganizationInfo setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
        return this;
    }

    public String getOrganizationType() {
        return organizationType;
    }

    public AccountOrganizationInfo setOrganizationType(String organizationType) {
        this.organizationType = organizationType;
        return this;
    }

    public int getOrganizationVisibility() {
        return organizationVisibility;
    }

    public AccountOrganizationInfo setOrganizationVisibility(Integer organizationVisibility) {
        this.organizationVisibility = organizationVisibility;
        return this;
    }

    public int getRole() {
        return role;
    }

    public AccountOrganizationInfo setRole(int role) {
        this.role = role;
        return this;
    }

    public String getRoleName() {
        return roleName;
    }

    public AccountOrganizationInfo setRoleName(String roleName) {
        this.roleName = roleName;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AccountOrganizationInfo that = (AccountOrganizationInfo) o;
        return oid == that.oid &&
                role == that.role &&
                Objects.equals(aid, that.aid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aid, oid, role);
    }
}
