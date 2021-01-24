package campuslifecenter.info.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class InfoComposite implements Serializable {
    private Long id;

    @ApiModelProperty(value = "info id, type 值必须为2")
    private Long pid;

    @ApiModelProperty(value = "顺序")
    private Integer compositeIndex;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public InfoComposite withId(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPid() {
        return pid;
    }

    public InfoComposite withPid(Long pid) {
        this.setPid(pid);
        return this;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

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
        sb.append(", id=").append(id);
        sb.append(", pid=").append(pid);
        sb.append(", compositeIndex=").append(compositeIndex);
        sb.append("]");
        return sb.toString();
    }
}