package campuslifecenter.usercenter.model;

import java.io.Serializable;
import java.util.List;

public class OrganizationInfo implements Serializable {

    private Integer id;
    private String name;
    private String type;
    private List<RoleInfo> roles;

    public Integer getId() {
        return id;
    }

    public OrganizationInfo setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public OrganizationInfo setName(String name) {
        this.name = name;
        return this;
    }

    public String getType() {
        return type;
    }

    public OrganizationInfo setType(String type) {
        this.type = type;
        return this;
    }

    public List<RoleInfo> getRoles() {
        return roles;
    }

    public OrganizationInfo setRoles(List<RoleInfo> roles) {
        this.roles = roles;
        return this;
    }
}
