package campuslifecenter.notice.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class NoticeTagKey implements Serializable {
    private Long nid;

    @ApiModelProperty(value = "标签内容")
    private String tag;

    private static final long serialVersionUID = 1L;

    public Long getNid() {
        return nid;
    }

    public NoticeTagKey withNid(Long nid) {
        this.setNid(nid);
        return this;
    }

    public void setNid(Long nid) {
        this.nid = nid;
    }

    public String getTag() {
        return tag;
    }

    public NoticeTagKey withTag(String tag) {
        this.setTag(tag);
        return this;
    }

    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", nid=").append(nid);
        sb.append(", tag=").append(tag);
        sb.append("]");
        return sb.toString();
    }
}