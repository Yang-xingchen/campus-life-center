package campuslifecenter.usercenter.model;

import campuslifecenter.usercenter.entry.Permission;
import campuslifecenter.usercenter.entry.Role;

import java.io.Serializable;
import java.util.List;

public class RoleInfo extends Role {

    private List<Permission> permissions;

    public List<Permission> getPermissions() {
        return permissions;
    }

    public RoleInfo setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
        return this;
    }
}
