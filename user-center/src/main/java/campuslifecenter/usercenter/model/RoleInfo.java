package campuslifecenter.usercenter.model;

import campuslifecenter.usercenter.entry.Permission;

import java.io.Serializable;
import java.util.List;

public class RoleInfo implements Serializable {
    private int id;
    private String name;
    private List<Permission> permissions;

    public int getId() {
        return id;
    }

    public RoleInfo setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public RoleInfo setName(String name) {
        this.name = name;
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
