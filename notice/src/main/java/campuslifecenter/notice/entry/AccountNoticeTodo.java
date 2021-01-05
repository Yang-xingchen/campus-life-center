package campuslifecenter.notice.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class AccountNoticeTodo extends AccountNoticeTodoKey implements Serializable {
    private Boolean finish;

    private Boolean top;

    private Boolean addList;

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

    public Boolean getTop() {
        return top;
    }

    public AccountNoticeTodo withTop(Boolean top) {
        this.setTop(top);
        return this;
    }

    public void setTop(Boolean top) {
        this.top = top;
    }

    public Boolean getAddList() {
        return addList;
    }

    public AccountNoticeTodo withAddList(Boolean addList) {
        this.setAddList(addList);
        return this;
    }

    public void setAddList(Boolean addList) {
        this.addList = addList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", finish=").append(finish);
        sb.append(", top=").append(top);
        sb.append(", addList=").append(addList);
        sb.append("]");
        return sb.toString();
    }
}