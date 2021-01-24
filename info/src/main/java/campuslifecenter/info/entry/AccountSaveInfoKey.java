package campuslifecenter.info.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class AccountSaveInfoKey implements Serializable {
    private String aid;

    private Long id;

    private Integer multipleIndex;

    private static final long serialVersionUID = 1L;

    public String getAid() {
        return aid;
    }

    public AccountSaveInfoKey withAid(String aid) {
        this.setAid(aid);
        return this;
    }

    public void setAid(String aid) {
        this.aid = aid == null ? null : aid.trim();
    }

    public Long getId() {
        return id;
    }

    public AccountSaveInfoKey withId(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMultipleIndex() {
        return multipleIndex;
    }

    public AccountSaveInfoKey withMultipleIndex(Integer multipleIndex) {
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
        sb.append(", aid=").append(aid);
        sb.append(", id=").append(id);
        sb.append(", multipleIndex=").append(multipleIndex);
        sb.append("]");
        return sb.toString();
    }
}