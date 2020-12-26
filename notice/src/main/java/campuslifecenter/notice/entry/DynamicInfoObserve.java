package campuslifecenter.notice.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class DynamicInfoObserve extends DynamicInfoObserveKey implements Serializable {
    private Integer type;

    private String value;

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

    public String getValue() {
        return value;
    }

    public DynamicInfoObserve withValue(String value) {
        this.setValue(value);
        return this;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", type=").append(type);
        sb.append(", value=").append(value);
        sb.append("]");
        return sb.toString();
    }
}