package campuslifecenter.usercenter.model;

import campuslifecenter.usercenter.entry.Account;
import campuslifecenter.usercenter.entry.Permission;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class AccountInfo implements Serializable {

    @ApiModelProperty("账户登录id")
    private String signId;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("性别")
    private String gender;

    @ApiModelProperty("创建时间")
    private Date createData;

    @ApiModelProperty("登录id")
    private String token;

    @ApiModelProperty("组织")
    private List<OrganizationInfo> organizations;

    private static final long serialVersionUID = 1L;

    public static class OrganizationInfo implements Serializable {

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

    public static class RoleInfo implements Serializable {
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

    public static AccountInfo withAccount(Account account) {
        return new AccountInfo()
                .setSignId(account.getSignId())
                .setName(account.getName())
                .setGender(account.getGender())
                .setCreateData(account.getCreateData());
    }

    public String getSignId() {
        return signId;
    }

    public AccountInfo setSignId(String signId) {
        this.signId = signId;
        return this;
    }

    public String getName() {
        return name;
    }

    public AccountInfo setName(String name) {
        this.name = name;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public AccountInfo setGender(Integer gender) {
        switch (gender) {
            case 0 -> this.gender = "女";
            case 1 -> this.gender = "男";
            case 2 -> this.gender = "保密";
            default -> throw new IllegalArgumentException(gender + " is undefined");
        }
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getCreateData() {
        return createData;
    }

    public AccountInfo setCreateData(Date createData) {
        this.createData = createData;
        return this;
    }

    public List<OrganizationInfo> getOrganizations() {
        return organizations;
    }

    public AccountInfo setOrganizations(List<OrganizationInfo> organizations) {
        this.organizations = organizations;
        return this;
    }

    public String getToken() {
        return token;
    }

    public AccountInfo setToken(String token) {
        this.token = token;
        return this;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AccountInfo that = (AccountInfo) o;
        return Objects.equals(signId, that.signId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(signId);
    }

    @Override
    public String toString() {
        return "AccountInfo{" +
                "signId='" + signId + '\'' +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", createData=" + createData +
                '}';
    }
}
