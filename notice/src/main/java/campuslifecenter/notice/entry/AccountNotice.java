package campuslifecenter.notice.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class AccountNotice extends AccountNoticeKey implements Serializable {
    private Boolean isRead;

    private Boolean isTop;

    private Boolean isDelete;

    private Integer relativeImportance;

    private static final long serialVersionUID = 1L;

    public Boolean getIsRead() {
        return isRead;
    }

    public AccountNotice withIsRead(Boolean isRead) {
        this.setIsRead(isRead);
        return this;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public Boolean getIsTop() {
        return isTop;
    }

    public AccountNotice withIsTop(Boolean isTop) {
        this.setIsTop(isTop);
        return this;
    }

    public void setIsTop(Boolean isTop) {
        this.isTop = isTop;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public AccountNotice withIsDelete(Boolean isDelete) {
        this.setIsDelete(isDelete);
        return this;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getRelativeImportance() {
        return relativeImportance;
    }

    public AccountNotice withRelativeImportance(Integer relativeImportance) {
        this.setRelativeImportance(relativeImportance);
        return this;
    }

    public void setRelativeImportance(Integer relativeImportance) {
        this.relativeImportance = relativeImportance;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", isRead=").append(isRead);
        sb.append(", isTop=").append(isTop);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", relativeImportance=").append(relativeImportance);
        sb.append("]");
        return sb.toString();
    }
}