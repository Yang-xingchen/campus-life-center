package campuslifecenter.notice.model;

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
    private List<Organization> organizations;

    private static final long serialVersionUID = 1L;

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
        this.gender = gender == 1 ? "男" : "女";
        return this;
    }

    public Date getCreateData() {
        return createData;
    }

    public AccountInfo setCreateData(Date createData) {
        this.createData = createData;
        return this;
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    public AccountInfo setOrganizations(List<Organization> organizations) {
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
