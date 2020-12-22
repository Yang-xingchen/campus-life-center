package campuslifecenter.notice.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class NoticeMateKey implements Serializable {
    @ApiModelProperty(value = "通知id")
    private Long nid;

    @ApiModelProperty(value = "类型: 0, 简单值, 见value字段; 1,按钮; 2, 收集信息")
    private Integer type;

    private static final long serialVersionUID = 1L;

    public Long getNid() {
        return nid;
    }

    public NoticeMateKey withNid(Long nid) {
        this.setNid(nid);
        return this;
    }

    public void setNid(Long nid) {
        this.nid = nid;
    }

    public Integer getType() {
        return type;
    }

    public NoticeMateKey withType(Integer type) {
        this.setType(type);
        return this;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", nid=").append(nid);
        sb.append(", type=").append(type);
        sb.append("]");
        return sb.toString();
    }
}