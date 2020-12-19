package campuslifecenter.usercenter.model;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class SecurityRequest implements Serializable {
    @ApiModelProperty("账户id")
    private String aid;
    @ApiModelProperty("安全密码")
    private String pwd;
    @ApiModelProperty("密钥")
    private String key;

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
