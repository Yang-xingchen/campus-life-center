package campuslifecenter.info.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class AccountSubmitKey implements Serializable {
    private String ref;

    private Long id;

    private String aid;

    private Integer multipleIndex;

    private static final long serialVersionUID = 1L;

    public String getRef() {
        return ref;
    }

    public AccountSubmitKey withRef(String ref) {
        this.setRef(ref);
        return this;
    }

    public void setRef(String ref) {
        this.ref = ref == null ? null : ref.trim();
    }

    public Long getId() {
        return id;
    }

    public AccountSubmitKey withId(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAid() {
        return aid;
    }

    public AccountSubmitKey withAid(String aid) {
        this.setAid(aid);
        return this;
    }

    public void setAid(String aid) {
        this.aid = aid == null ? null : aid.trim();
    }

    public Integer getMultipleIndex() {
        return multipleIndex;
    }

    public AccountSubmitKey withMultipleIndex(Integer multipleIndex) {
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
        sb.append(", ref=").append(ref);
        sb.append(", id=").append(id);
        sb.append(", aid=").append(aid);
        sb.append(", multipleIndex=").append(multipleIndex);
        sb.append("]");
        return sb.toString();
    }
}