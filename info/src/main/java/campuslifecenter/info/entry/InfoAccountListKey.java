package campuslifecenter.info.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class InfoAccountListKey implements Serializable {
    private String source;

    private Long id;

    private String aid;

    private Integer multipleIndex;

    private static final long serialVersionUID = 1L;

    public String getSource() {
        return source;
    }

    public InfoAccountListKey withSource(String source) {
        this.setSource(source);
        return this;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public Long getId() {
        return id;
    }

    public InfoAccountListKey withId(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAid() {
        return aid;
    }

    public InfoAccountListKey withAid(String aid) {
        this.setAid(aid);
        return this;
    }

    public void setAid(String aid) {
        this.aid = aid == null ? null : aid.trim();
    }

    public Integer getMultipleIndex() {
        return multipleIndex;
    }

    public InfoAccountListKey withMultipleIndex(Integer multipleIndex) {
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
        sb.append(", source=").append(source);
        sb.append(", id=").append(id);
        sb.append(", aid=").append(aid);
        sb.append(", multipleIndex=").append(multipleIndex);
        sb.append("]");
        return sb.toString();
    }
}