package campuslifecenter.notice.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class NoticeMateKey implements Serializable {
    @ApiModelProperty(value = "通知id")
    private Integer nid;

    @ApiModelProperty(value = "类型: 0: 简单值, 见value字段; 1:按钮; 2: 收集信息")
    private Byte type;

    private static final long serialVersionUID = 1L;

    public Integer getNid() {
        return nid;
    }

    public NoticeMateKey withNid(Integer nid) {
        this.setNid(nid);
        return this;
    }

    public void setNid(Integer nid) {
        this.nid = nid;
    }

    public Byte getType() {
        return type;
    }

    public NoticeMateKey withType(Byte type) {
        this.setType(type);
        return this;
    }

    public void setType(Byte type) {
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