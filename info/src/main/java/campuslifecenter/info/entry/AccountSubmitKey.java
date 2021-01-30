package campuslifecenter.info.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class AccountSubmitKey implements Serializable {
    @ApiModelProperty(value = "根信息id, 区分不同来源")
    private Long root;

    private Long id;

    private String aid;

    private Integer multipleIndex;

    private static final long serialVersionUID = 1L;

    public Long getRoot() {
        return root;
    }

    public AccountSubmitKey withRoot(Long root) {
        this.setRoot(root);
        return this;
    }

    public void setRoot(Long root) {
        this.root = root;
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
        sb.append(", root=").append(root);
        sb.append(", id=").append(id);
        sb.append(", aid=").append(aid);
        sb.append(", multipleIndex=").append(multipleIndex);
        sb.append("]");
        return sb.toString();
    }
}