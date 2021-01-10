package campuslifecenter.info.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class InfoList extends InfoListKey implements Serializable {
    private Integer listOrder;

    private static final long serialVersionUID = 1L;

    public Integer getListOrder() {
        return listOrder;
    }

    public InfoList withListOrder(Integer listOrder) {
        this.setListOrder(listOrder);
        return this;
    }

    public void setListOrder(Integer listOrder) {
        this.listOrder = listOrder;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", listOrder=").append(listOrder);
        sb.append("]");
        sb.append(", from super class ");
        sb.append(super.toString());
        return sb.toString();
    }
}