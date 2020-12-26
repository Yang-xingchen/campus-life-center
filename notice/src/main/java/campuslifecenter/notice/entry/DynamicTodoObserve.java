package campuslifecenter.notice.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class DynamicTodoObserve extends DynamicTodoObserveKey implements Serializable {
    private Boolean isFinish;

    private static final long serialVersionUID = 1L;

    public Boolean getIsFinish() {
        return isFinish;
    }

    public DynamicTodoObserve withIsFinish(Boolean isFinish) {
        this.setIsFinish(isFinish);
        return this;
    }

    public void setIsFinish(Boolean isFinish) {
        this.isFinish = isFinish;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", isFinish=").append(isFinish);
        sb.append("]");
        return sb.toString();
    }
}