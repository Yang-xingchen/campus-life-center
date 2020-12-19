package campuslifecenter.notice.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class NoticeMate extends NoticeMateKey implements Serializable {
    @ApiModelProperty(value = "简单值的值")
    private String value;

    private static final long serialVersionUID = 1L;

    public String getValue() {
        return value;
    }

    public NoticeMate withValue(String value) {
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
        sb.append(", value=").append(value);
        sb.append("]");
        return sb.toString();
    }
}