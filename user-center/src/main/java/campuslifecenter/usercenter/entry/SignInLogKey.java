package campuslifecenter.usercenter.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class SignInLogKey implements Serializable {
    @ApiModelProperty(value = "id")
    private String aid;

    private Date signInTime;

    private static final long serialVersionUID = 1L;

    public String getAid() {
        return aid;
    }

    public SignInLogKey withAid(String aid) {
        this.setAid(aid);
        return this;
    }

    public void setAid(String aid) {
        this.aid = aid == null ? null : aid.trim();
    }

    public Date getSignInTime() {
        return signInTime;
    }

    public SignInLogKey withSignInTime(Date signInTime) {
        this.setSignInTime(signInTime);
        return this;
    }

    public void setSignInTime(Date signInTime) {
        this.signInTime = signInTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", aid=").append(aid);
        sb.append(", signInTime=").append(signInTime);
        sb.append("]");
        return sb.toString();
    }
}