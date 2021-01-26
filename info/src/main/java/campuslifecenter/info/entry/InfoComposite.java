package campuslifecenter.info.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class InfoComposite extends InfoCompositeKey implements Serializable {
    @ApiModelProperty(value = "顺序")
    private Integer compositeIndex;

    private static final long serialVersionUID = 1L;

    public Integer getCompositeIndex() {
        return compositeIndex;
    }

    public InfoComposite withCompositeIndex(Integer compositeIndex) {
        this.setCompositeIndex(compositeIndex);
        return this;
    }

    public void setCompositeIndex(Integer compositeIndex) {
        this.compositeIndex = compositeIndex;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", compositeIndex=").append(compositeIndex);
        sb.append("]");
        sb.append(", from super class ");
        sb.append(super.toString());
        return sb.toString();
    }
}