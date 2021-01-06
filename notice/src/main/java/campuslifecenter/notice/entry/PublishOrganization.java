package campuslifecenter.notice.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class PublishOrganization extends PublishOrganizationKey implements Serializable {
    private Boolean dynamic;

    private Boolean belong;

    private Boolean subscribe;

    private static final long serialVersionUID = 1L;

    public Boolean getDynamic() {
        return dynamic;
    }

    public PublishOrganization withDynamic(Boolean dynamic) {
        this.setDynamic(dynamic);
        return this;
    }

    public void setDynamic(Boolean dynamic) {
        this.dynamic = dynamic;
    }

    public Boolean getBelong() {
        return belong;
    }

    public PublishOrganization withBelong(Boolean belong) {
        this.setBelong(belong);
        return this;
    }

    public void setBelong(Boolean belong) {
        this.belong = belong;
    }

    public Boolean getSubscribe() {
        return subscribe;
    }

    public PublishOrganization withSubscribe(Boolean subscribe) {
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
        sb.append(", dynamic=").append(dynamic);
        sb.append(", belong=").append(belong);
        sb.append(", subscribe=").append(subscribe);
        sb.append("]");
        sb.append(", from super class ");
        sb.append(super.toString());
        return sb.toString();
    }
}