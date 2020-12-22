package campuslifecenter.usercenter.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class SecurityLogKey implements Serializable {
    @ApiModelProperty(value = "账户id")
    private String aid;

    @ApiModelProperty(value = "进入时间")
    private Date inputTime;

    private static final long serialVersionUID = 1L;

    public String getAid() {
        return aid;
    }

    public SecurityLogKey withAid(String aid) {
        this.setAid(aid);
        return this;
    }

    public void setAid(String aid) {
        this.aid = aid == null ? null : aid.trim();
    }

    public Date getInputTime() {
        return inputTime;
    }

    public SecurityLogKey withInputTime(Date inputTime) {
        this.setInputTime(inputTime);
        return this;
    }

    public void setInputTime(Date inputTime) {
        this.inputTime = inputTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", aid=").append(aid);
        sb.append(", inputTime=").append(inputTime);
        sb.append("]");
        return sb.toString();
    }
}