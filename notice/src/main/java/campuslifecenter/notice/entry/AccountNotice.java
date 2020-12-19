package campuslifecenter.notice.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class AccountNotice extends AccountNoticeKey implements Serializable {
    @ApiModelProperty(value = "是否已读")
    private Byte read;

    @ApiModelProperty(value = "是否置顶")
    private Byte top;

    @ApiModelProperty(value = "是否删除")
    private Byte delete;

    @ApiModelProperty(value = "相对重要程度")
    private Byte relativeImportance;

    private static final long serialVersionUID = 1L;

    public Byte getRead() {
        return read;
    }

    public AccountNotice withRead(Byte read) {
        this.setRead(read);
        return this;
    }

    public void setRead(Byte read) {
        this.read = read;
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

    public Byte getDelete() {
        return delete;
    }

    public AccountNotice withDelete(Byte delete) {
        this.setDelete(delete);
        return this;
    }

    public void setDelete(Byte delete) {
        this.delete = delete;
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
        sb.append(", read=").append(read);
        sb.append(", top=").append(top);
        sb.append(", delete=").append(delete);
        sb.append(", relativeImportance=").append(relativeImportance);
        sb.append("]");
        return sb.toString();
    }
}