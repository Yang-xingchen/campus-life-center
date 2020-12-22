package campuslifecenter.notice.model;

import java.io.Serializable;
import java.util.Objects;

public class AccountOrganizationInfo implements Serializable {

    private String aid;
    private int oid;
    private String organizationName;
    private String type;
    private int role;
    private String roleName;

    public String getAid() {
        return aid;
    }

    public AccountOrganizationInfo setAid(String aid) {
        this.aid = aid;
        return this;
    }

    public int getOid() {
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

    public String getType() {
        return type;
    }

    public AccountOrganizationInfo setType(String type) {
        this.type = type;
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
