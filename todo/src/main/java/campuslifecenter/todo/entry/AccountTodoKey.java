package campuslifecenter.todo.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class AccountTodoKey implements Serializable {
    @ApiModelProperty(value = "todo id")
    private Long id;

    @ApiModelProperty(value = "账户id")
    private String aid;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public AccountTodoKey withId(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAid() {
        return aid;
    }

    public AccountTodoKey withAid(String aid) {
        this.setAid(aid);
        return this;
    }

    public void setAid(String aid) {
        this.aid = aid == null ? null : aid.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", aid=").append(aid);
        sb.append("]");
        return sb.toString();
    }
}