package campuslifecenter.notice.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class AccountNotice extends AccountNoticeKey implements Serializable {
    private Byte isRead;

    private Byte top;

    private Byte isDelete;

    private Byte relativeImportance;

    private static final long serialVersionUID = 1L;

    public Byte getIsRead() {
        return isRead;
    }

    public AccountNotice withIsRead(Byte isRead) {
        this.setIsRead(isRead);
        return this;
    }

    public void setIsRead(Byte isRead) {
        this.isRead = isRead;
    }

    public Byte getTop() {
        return top;
    }

    public AccountNotice withTop(Byte top) {
        this.setTop(top);
        return this;
    }

    public void setTop(Byte top) {
        this.top = top;
    }

    public Byte getIsDelete() {
        return isDelete;
    }

    public AccountNotice withIsDelete(Byte isDelete) {
        this.setIsDelete(isDelete);
        return this;
    }

    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }

    public Byte getRelativeImportance() {
        return relativeImportance;
    }

    public AccountNotice withRelativeImportance(Byte relativeImportance) {
        this.setRelativeImportance(relativeImportance);
        return this;
    }

    public void setRelativeImportance(Byte relativeImportance) {
        this.relativeImportance = relativeImportance;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", isRead=").append(isRead);
        sb.append(", top=").append(top);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", relativeImportance=").append(relativeImportance);
        sb.append("]");
        return sb.toString();
    }
}