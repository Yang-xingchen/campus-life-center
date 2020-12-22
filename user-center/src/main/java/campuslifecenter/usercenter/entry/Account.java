package campuslifecenter.usercenter.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class Account implements Serializable {
    @ApiModelProperty(value = "登录id")
    private String signId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "性别")
    private Integer gender;

    @ApiModelProperty(value = "创建日期")
    private Date createData;

    @ApiModelProperty(value = "安全密码")
    private String securityKey;

    private static final long serialVersionUID = 1L;

    public String getSignId() {
        return signId;
    }

    public Account withSignId(String signId) {
        this.setSignId(signId);
        return this;
    }

    public void setSignId(String signId) {
        this.signId = signId == null ? null : signId.trim();
    }

    public String getName() {
        return name;
    }

    public Account withName(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPassword() {
        return password;
    }

    public Account withPassword(String password) {
        this.setPassword(password);
        return this;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getGender() {
        return gender;
    }

    public Account withGender(Integer gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Date getCreateData() {
        return createData;
    }

    public Account withCreateData(Date createData) {
        this.setCreateData(createData);
        return this;
    }

    public void setCreateData(Date createData) {
        this.createData = createData;
    }

    public String getSecurityKey() {
        return securityKey;
    }

    public Account withSecurityKey(String securityKey) {
        this.setSecurityKey(securityKey);
        return this;
    }

    public void setSecurityKey(String securityKey) {
        this.securityKey = securityKey == null ? null : securityKey.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", signId=").append(signId);
        sb.append(", name=").append(name);
        sb.append(", password=").append(password);
        sb.append(", gender=").append(gender);
        sb.append(", createData=").append(createData);
        sb.append(", securityKey=").append(securityKey);
        sb.append("]");
        return sb.toString();
    }
}