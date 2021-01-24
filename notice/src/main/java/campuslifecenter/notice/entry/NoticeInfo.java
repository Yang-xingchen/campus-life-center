package campuslifecenter.notice.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class NoticeInfo extends NoticeInfoKey implements Serializable {
    private Long rootId;

    private static final long serialVersionUID = 1L;

    public Long getRootId() {
        return rootId;
    }

    public NoticeInfo withRootId(Long rootId) {
        this.setRootId(rootId);
        return this;
    }

    public void setRootId(Long rootId) {
        this.rootId = rootId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", rootId=").append(rootId);
        sb.append("]");
        sb.append(", from super class ");
        sb.append(super.toString());
        return sb.toString();
    }
}