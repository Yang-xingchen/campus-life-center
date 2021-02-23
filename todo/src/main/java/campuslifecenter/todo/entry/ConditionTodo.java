package campuslifecenter.todo.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class ConditionTodo implements Serializable {
    @ApiModelProperty(value = "引用")
    private String ref;

    @ApiModelProperty(value = "todo id")
    private Long tid;

    @ApiModelProperty(value = "是否完成")
    private Boolean finish;

    private Boolean dynamic;

    private static final long serialVersionUID = 1L;

    public String getRef() {
        return ref;
    }

    public ConditionTodo withRef(String ref) {
        this.setRef(ref);
        return this;
    }

    public void setRef(String ref) {
        this.ref = ref == null ? null : ref.trim();
    }

    public Long getTid() {
        return tid;
    }

    public ConditionTodo withTid(Long tid) {
        this.setTid(tid);
        return this;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

    public Boolean getFinish() {
        return finish;
    }

    public ConditionTodo withFinish(Boolean finish) {
        this.setFinish(finish);
        return this;
    }

    public void setFinish(Boolean finish) {
        this.finish = finish;
    }

    public Boolean getDynamic() {
        return dynamic;
    }

    public ConditionTodo withDynamic(Boolean dynamic) {
        this.setDynamic(dynamic);
        return this;
    }

    public void setDynamic(Boolean dynamic) {
        this.dynamic = dynamic;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", ref=").append(ref);
        sb.append(", tid=").append(tid);
        sb.append(", finish=").append(finish);
        sb.append(", dynamic=").append(dynamic);
        sb.append("]");
        return sb.toString();
    }
}