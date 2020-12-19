package campuslifecenter.notice.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class NoticeConditionKey implements Serializable {
    @ApiModelProperty(value = "通知id")
    private Integer nid;

    @ApiModelProperty(value = "条件类型: 0:二值按钮; 1:个人信息")
    private Byte conditionType;

    private static final long serialVersionUID = 1L;

    public Integer getNid() {
        return nid;
    }

    public NoticeConditionKey withNid(Integer nid) {
        this.setNid(nid);
        return this;
    }

    public void setNid(Integer nid) {
        this.nid = nid;
    }

    public Byte getConditionType() {
        return conditionType;
    }

    public NoticeConditionKey withConditionType(Byte conditionType) {
        this.setConditionType(conditionType);
        return this;
    }

    public void setConditionType(Byte conditionType) {
        this.conditionType = conditionType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", nid=").append(nid);
        sb.append(", conditionType=").append(conditionType);
        sb.append("]");
        return sb.toString();
    }
}