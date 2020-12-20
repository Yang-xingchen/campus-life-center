package campuslifecenter.usercenter.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class RolePermissionKey implements Serializable {
    @ApiModelProperty(value = "id")
    private Integer oid;

    @ApiModelProperty(value = "id")
    private Integer rid;

    @ApiModelProperty(value = "id")
    private Integer pid;

    private static final long serialVersionUID = 1L;

    public Integer getOid() {
        return oid;
    }

    public RolePermissionKey withOid(Integer oid) {
        this.setOid(oid);
        return this;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public Integer getRid() {
        return rid;
    }

    public RolePermissionKey withRid(Integer rid) {
        this.setRid(rid);
        return this;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public Integer getPid() {
        return pid;
    }

    public RolePermissionKey withPid(Integer pid) {
        this.setPid(pid);
        return this;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", oid=").append(oid);
        sb.append(", rid=").append(rid);
        sb.append(", pid=").append(pid);
        sb.append("]");
        return sb.toString();
    }
}