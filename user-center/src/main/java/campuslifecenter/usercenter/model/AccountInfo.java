package campuslifecenter.usercenter.model;

import campuslifecenter.usercenter.entry.Account;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class AccountInfo implements Serializable {

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

    public static AccountInfo withAccount(Account account) {
        return new AccountInfo()
                .setId(account.getId())
                .setName(account.getName())
                .setGender(account.getGender())
                .setCreateData(account.getCreateData());
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
