package campuslifecenter.todo.service;

import campuslifecenter.todo.model.Response;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.Serializable;
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
        private List<AccountOrganizationInfo> organizations;

        private static final long serialVersionUID = 1L;

        public static class AccountOrganizationInfo implements Serializable {

            private String aid;
            private Integer oid;
            private String organizationName;
            private String organizationType;
            private int organizationVisibility;
            private int role;
            private String roleName;

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

        public AccountInfo setGender(Byte gender) {
            switch (gender) {
                case 0 ->this.gender = "女";
                case 1 ->this.gender = "男";
                case 2 ->this.gender = "保密";
                default ->throw new IllegalArgumentException(gender + " is undefined");
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

        public List<AccountOrganizationInfo> getOrganizations() {
            return organizations;
        }

        public AccountInfo setOrganizations(List<AccountOrganizationInfo> organizations) {
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
}
