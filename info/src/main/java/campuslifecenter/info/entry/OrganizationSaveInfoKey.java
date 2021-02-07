package campuslifecenter.info.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class OrganizationSaveInfoKey implements Serializable {
    private Integer oid;

    private Long id;

    private Integer multipleIndex;

    private static final long serialVersionUID = 1L;

    public Integer getOid() {
        return oid;
    }

    public OrganizationSaveInfoKey withOid(Integer oid) {
        this.setOid(oid);
        return this;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public Long getId() {
        return id;
    }

    public OrganizationSaveInfoKey withId(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMultipleIndex() {
        return multipleIndex;
    }

    public OrganizationSaveInfoKey withMultipleIndex(Integer multipleIndex) {
        this.setMultipleIndex(multipleIndex);
        return this;
    }

    public void setMultipleIndex(Integer multipleIndex) {
        this.multipleIndex = multipleIndex;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", oid=").append(oid);
        sb.append(", id=").append(id);
        sb.append(", multipleIndex=").append(multipleIndex);
        sb.append("]");
        return sb.toString();
    }
}