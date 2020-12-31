package campuslifecenter.notice.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class DynamicInfoObserve extends DynamicInfoObserveKey implements Serializable {
    private Integer type;

    private String typeValue;

    private static final long serialVersionUID = 1L;

    public Integer getType() {
        return type;
    }

    public DynamicInfoObserve withType(Integer type) {
        this.setType(type);
        return this;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeValue() {
        return typeValue;
    }

    public DynamicInfoObserve withTypeValue(String typeValue) {
        this.setTypeValue(typeValue);
        return this;
    }

    public void setTypeValue(String typeValue) {
        this.typeValue = typeValue == null ? null : typeValue.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", type=").append(type);
        sb.append(", typeValue=").append(typeValue);
        sb.append("]");
        return sb.toString();
    }
}