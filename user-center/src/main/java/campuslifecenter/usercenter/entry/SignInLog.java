package campuslifecenter.usercenter.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class SignInLog extends SignInLogKey implements Serializable {
    @ApiModelProperty(value = "登录id")
    private String signInId;

    @ApiModelProperty(value = "退出时间")
    private Date signOutTime;

    @ApiModelProperty(value = "登录ip")
    private String ip;

    @ApiModelProperty(value = "登录途径")
    private Integer source;

    @ApiModelProperty(value = "退出登录类型")
    private Integer type;

    @ApiModelProperty(value = "登录时token")
    private String token;

    private static final long serialVersionUID = 1L;

    public String getSignInId() {
        return signInId;
    }

    public SignInLog withSignInId(String signInId) {
        this.setSignInId(signInId);
        return this;
    }

    public void setSignInId(String signInId) {
        this.signInId = signInId == null ? null : signInId.trim();
    }

    public Date getSignOutTime() {
        return signOutTime;
    }

    public SignInLog withSignOutTime(Date signOutTime) {
        this.setSignOutTime(signOutTime);
        return this;
    }

    public void setSignOutTime(Date signOutTime) {
        this.signOutTime = signOutTime;
    }

    public String getIp() {
        return ip;
    }

    public SignInLog withIp(String ip) {
        this.setIp(ip);
        return this;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public Integer getSource() {
        return source;
    }

    public SignInLog withSource(Integer source) {
        this.setSource(source);
        return this;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getType() {
        return type;
    }

    public SignInLog withType(Integer type) {
        this.setType(type);
        return this;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public SignInLog withToken(String token) {
        this.setToken(token);
        return this;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", signInId=").append(signInId);
        sb.append(", signOutTime=").append(signOutTime);
        sb.append(", ip=").append(ip);
        sb.append(", source=").append(source);
        sb.append(", type=").append(type);
        sb.append(", token=").append(token);
        sb.append("]");
        return sb.toString();
    }
}