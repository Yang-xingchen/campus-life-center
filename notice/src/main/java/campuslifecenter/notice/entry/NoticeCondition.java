package campuslifecenter.notice.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class NoticeCondition implements Serializable {
    @ApiModelProperty(value = "条件引用")
    private String ref;

    @ApiModelProperty(value = "通知id")
    private Long nid;

    @ApiModelProperty(value = "来源: 0,成员; 1, 组织; 2,待办; 3,信息")
    private Integer type;

    private static final long serialVersionUID = 1L;

    public String getRef() {
        return ref;
    }

    public NoticeCondition withRef(String ref) {
        this.setRef(ref);
        return this;
    }

    public void setRef(String ref) {
        this.ref = ref == null ? null : ref.trim();
    }

    public Long getNid() {
        return nid;
    }

    public NoticeCondition withNid(Long nid) {
        this.setNid(nid);
        return this;
    }

    public void setNid(Long nid) {
        this.nid = nid;
    }

    public Integer getType() {
        return type;
    }

    public NoticeCondition withType(Integer type) {
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
        sb.append(", ref=").append(ref);
        sb.append(", nid=").append(nid);
        sb.append(", type=").append(type);
        sb.append("]");
        return sb.toString();
    }
}