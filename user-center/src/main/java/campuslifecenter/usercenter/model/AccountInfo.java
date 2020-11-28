package campuslifecenter.usercenter.model;

import campuslifecenter.usercenter.entry.Organization;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class AccountInfo implements Serializable {

    private String signId;

    private String name;

    private Byte gender;

    private Date createData;

    private String cookie;

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

    public Byte getGender() {
        return gender;
    }

    public AccountInfo setGender(Byte gender) {
        this.gender = gender;
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

    public String getCookie() {
        return cookie;
    }

    public AccountInfo setCookie(String cookie) {
        this.cookie = cookie;
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
