package campuslifecenter.usercenter.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class SecurityLogKey implements Serializable {
    @ApiModelProperty(value = "账户id")
    private String aid;

    @ApiModelProperty(value = "开始时间")
    private Date startTime;

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

    public Date getStartTime() {
        return startTime;
    }

    public SecurityLogKey withStartTime(Date startTime) {
        this.setStartTime(startTime);
        return this;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", aid=").append(aid);
        sb.append(", startTime=").append(startTime);
        sb.append("]");
        return sb.toString();
    }
}