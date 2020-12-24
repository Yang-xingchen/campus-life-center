package campuslifecenter.notice.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class AccountNoticeTodo extends AccountNoticeTodoKey implements Serializable {
    @ApiModelProperty(value = "是否完成")
    private Boolean finish;

    @ApiModelProperty(value = "是否置顶")
    private Boolean isTop;

    @ApiModelProperty(value = "是否加入列表")
    private Boolean isAdd;

    private static final long serialVersionUID = 1L;

    public Boolean getFinish() {
        return finish;
    }

    public AccountNoticeTodo withFinish(Boolean finish) {
        this.setFinish(finish);
        return this;
    }

    public void setFinish(Boolean finish) {
        this.finish = finish;
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
        sb.append(", finish=").append(finish);
        sb.append(", isTop=").append(isTop);
        sb.append(", isAdd=").append(isAdd);
        sb.append("]");
        return sb.toString();
    }
}