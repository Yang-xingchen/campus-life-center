package campuslifecenter.notice.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class PublishTodo extends PublishTodoKey implements Serializable {
    private Boolean dynamic;

    private Boolean finish;

    private static final long serialVersionUID = 1L;

    public Boolean getDynamic() {
        return dynamic;
    }

    public PublishTodo withDynamic(Boolean dynamic) {
        this.setDynamic(dynamic);
        return this;
    }

    public void setDynamic(Boolean dynamic) {
        this.dynamic = dynamic;
    }

    public Boolean getFinish() {
        return finish;
    }

    public PublishTodo withFinish(Boolean finish) {
        this.setFinish(finish);
        return this;
    }

    public void setFinish(Boolean finish) {
        this.finish = finish;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", dynamic=").append(dynamic);
        sb.append(", finish=").append(finish);
        sb.append("]");
        sb.append(", from super class ");
        sb.append(super.toString());
        return sb.toString();
    }
}