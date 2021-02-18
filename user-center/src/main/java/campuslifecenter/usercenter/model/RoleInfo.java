package campuslifecenter.usercenter.model;

import campuslifecenter.usercenter.entry.Permission;
import campuslifecenter.usercenter.entry.Role;

import java.io.Serializable;
import java.util.List;

public class RoleInfo extends Role {

    private int oid;

    private List<Permission> permissions;

    public int getOid() {
        return oid;
    }

    public RoleInfo setOid(int oid) {
        this.oid = oid;
        return this;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public RoleInfo setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
        return this;
    }
}
