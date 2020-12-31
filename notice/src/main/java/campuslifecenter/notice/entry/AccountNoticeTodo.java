package campuslifecenter.notice.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class AccountNoticeTodo extends AccountNoticeTodoKey implements Serializable {
    private Boolean isFinish;

    private Boolean isTop;

    private Boolean isAdd;

    private static final long serialVersionUID = 1L;

    public Boolean getIsFinish() {
        return isFinish;
    }

    public AccountNoticeTodo withIsFinish(Boolean isFinish) {
        this.setIsFinish(isFinish);
        return this;
    }

    public void setIsFinish(Boolean isFinish) {
        this.isFinish = isFinish;
    }

    public Boolean getIsTop() {
        return isTop;
    }

    public AccountNoticeTodo withIsTop(Boolean isTop) {
        this.setIsTop(isTop);
        return this;
    }

    public void setIsTop(Boolean isTop) {
        this.isTop = isTop;
    }

    public Boolean getIsAdd() {
        return isAdd;
    }

    public AccountNoticeTodo withIsAdd(Boolean isAdd) {
        this.setIsAdd(isAdd);
        return this;
    }

    public void setIsAdd(Boolean isAdd) {
        this.isAdd = isAdd;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", isFinish=").append(isFinish);
        sb.append(", isTop=").append(isTop);
        sb.append(", isAdd=").append(isAdd);
        sb.append("]");
        return sb.toString();
    }
}