package campuslifecenter.usercenter.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class ConditionAccountKey implements Serializable {
    @ApiModelProperty(value = "引用")
    private String ref;

    @ApiModelProperty(value = "成员")
    private String aid;

    private static final long serialVersionUID = 1L;

    public String getRef() {
        return ref;
    }

    public ConditionAccountKey withRef(String ref) {
        this.setRef(ref);
        return this;
    }

    public void setRef(String ref) {
        this.ref = ref == null ? null : ref.trim();
    }

    public String getAid() {
        return aid;
    }

    public ConditionAccountKey withAid(String aid) {
        this.setAid(aid);
        return this;
    }

    public void setAid(String aid) {
        this.aid = aid == null ? null : aid.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", ref=").append(ref);
        sb.append(", aid=").append(aid);
        sb.append("]");
        return sb.toString();
    }
}