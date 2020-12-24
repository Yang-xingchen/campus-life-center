package campuslifecenter.notice.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class DynamicOrganizationObserve extends DynamicOrganizationObserveKey implements Serializable {
    private Boolean isBelong;

    private Boolean isSubscribe;

    private static final long serialVersionUID = 1L;

    public Boolean getIsBelong() {
        return isBelong;
    }

    public DynamicOrganizationObserve withIsBelong(Boolean isBelong) {
        this.setIsBelong(isBelong);
        return this;
    }

    public void setIsBelong(Boolean isBelong) {
        this.isBelong = isBelong;
    }

    public Boolean getIsSubscribe() {
        return isSubscribe;
    }

    public DynamicOrganizationObserve withIsSubscribe(Boolean isSubscribe) {
        this.setIsSubscribe(isSubscribe);
        return this;
    }

    public void setIsSubscribe(Boolean isSubscribe) {
        this.isSubscribe = isSubscribe;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", isBelong=").append(isBelong);
        sb.append(", isSubscribe=").append(isSubscribe);
        sb.append("]");
        return sb.toString();
    }
}