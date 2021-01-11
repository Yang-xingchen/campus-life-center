package campuslifecenter.notice.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class PublishOrganizationKey implements Serializable {
    @ApiModelProperty(value = "通知id")
    private Long nid;

    @ApiModelProperty(value = "组织id")
    private Integer oid;

    private static final long serialVersionUID = 1L;

    public Long getNid() {
        return nid;
    }

    public PublishOrganizationKey withNid(Long nid) {
        this.setNid(nid);
        return this;
    }

    public void setNid(Long nid) {
        this.nid = nid;
    }

    public Integer getOid() {
        return oid;
    }

    public PublishOrganizationKey withOid(Integer oid) {
        this.setOid(oid);
        return this;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", nid=").append(nid);
        sb.append(", oid=").append(oid);
        sb.append("]");
        return sb.toString();
    }
}