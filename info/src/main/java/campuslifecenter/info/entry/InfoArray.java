package campuslifecenter.info.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class InfoArray implements Serializable {
    private Long id;

    @ApiModelProperty(value = "info id, type 2")
    private Long pid;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public InfoArray withId(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPid() {
        return pid;
    }

    public InfoArray withPid(Long pid) {
        this.setPid(pid);
        return this;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", pid=").append(pid);
        sb.append("]");
        return sb.toString();
    }
}