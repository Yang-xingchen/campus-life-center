package campuslifecenter.notice.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class PublishInfo extends PublishInfoKey implements Serializable {
    private Boolean dynamic;

    private Integer type;

    private String typeValue;

    private static final long serialVersionUID = 1L;

    public Boolean getDynamic() {
        return dynamic;
    }

    public PublishInfo withDynamic(Boolean dynamic) {
        this.setDynamic(dynamic);
        return this;
    }

    public void setDynamic(Boolean dynamic) {
        this.dynamic = dynamic;
    }

    public Integer getType() {
        return type;
    }

    public PublishInfo withType(Integer type) {
        this.setType(type);
        return this;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeValue() {
        return typeValue;
    }

    public PublishInfo withTypeValue(String typeValue) {
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
        sb.append(", dynamic=").append(dynamic);
        sb.append(", type=").append(type);
        sb.append(", typeValue=").append(typeValue);
        sb.append("]");
        sb.append(", from super class ");
        sb.append(super.toString());
        return sb.toString();
    }
}