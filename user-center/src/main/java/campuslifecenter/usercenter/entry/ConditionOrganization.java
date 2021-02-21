package campuslifecenter.usercenter.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class ConditionOrganization implements Serializable {
    @ApiModelProperty(value = "引用")
    private String ref;

    @ApiModelProperty(value = "组织")
    private Integer oid;

    @ApiModelProperty(value = "属于")
    private Boolean belong;

    @ApiModelProperty(value = "关注")
    private Boolean subscribe;

    private static final long serialVersionUID = 1L;

    public String getRef() {
        return ref;
    }

    public ConditionOrganization withRef(String ref) {
        this.setRef(ref);
        return this;
    }

    public void setRef(String ref) {
        this.ref = ref == null ? null : ref.trim();
    }

    public Integer getOid() {
        return oid;
    }

    public ConditionOrganization withOid(Integer oid) {
        this.setOid(oid);
        return this;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public Boolean getBelong() {
        return belong;
    }

    public ConditionOrganization withBelong(Boolean belong) {
        this.setBelong(belong);
        return this;
    }

    public void setBelong(Boolean belong) {
        this.belong = belong;
    }

    public Boolean getSubscribe() {
        return subscribe;
    }

    public ConditionOrganization withSubscribe(Boolean subscribe) {
        this.setSubscribe(subscribe);
        return this;
    }

    public void setSubscribe(Boolean subscribe) {
        this.subscribe = subscribe;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", ref=").append(ref);
        sb.append(", oid=").append(oid);
        sb.append(", belong=").append(belong);
        sb.append(", subscribe=").append(subscribe);
        sb.append("]");
        return sb.toString();
    }
}