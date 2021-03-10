package campuslifecenter.info.service;

import campuslifecenter.common.model.Response;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.Serializable;
import java.security.Permission;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@FeignClient(name = "user-center", path = "/account", contextId = "account")
public interface AccountService {

    @GetMapping("/info/{token}")
    Response<AccountInfo> info(@ApiParam("token") @PathVariable String token);

    @GetMapping("/{id}/info")
    Response<AccountInfo> infoById(@ApiParam("id") @PathVariable String id);

    @PostMapping("/infos")
    Response<List<AccountInfo>> infoByIds(List<String> ids);

    class AccountInfo implements Serializable {

        @ApiModelProperty("账户登录id")
        private String id;

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

        public String getId() {
            return id;
        }

        public AccountInfo setId(String id) {
            this.id = id;
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

        private static transient final String[] GENDER_MAP = new String[]{"女", "男", "保密"};

        public AccountInfo setGender(Integer gender) {
            this.gender = GENDER_MAP[gender];
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
            return Objects.equals(id, that.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }

        @Override
        public String toString() {
            return "AccountInfo{" +
                    "signId='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", gender=" + gender +
                    ", createData=" + createData +
                    '}';
        }
    }
}
