package campuslifecenter.notice.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class AccountNotice extends AccountNoticeKey implements Serializable {
    @ApiModelProperty(value = "是否已读")
    private Boolean looked;

    @ApiModelProperty(value = "是否置顶")
    private Boolean top;

    @ApiModelProperty(value = "是否删除")
    private Boolean del;

    @ApiModelProperty(value = "相对重要程度")
    private Integer relativeImportance;

    private static final long serialVersionUID = 1L;

    public Boolean getLooked() {
        return looked;
    }

    public AccountNotice withLooked(Boolean looked) {
        this.setLooked(looked);
        return this;
    }

    public void setLooked(Boolean looked) {
        this.looked = looked;
    }

    public Boolean getTop() {
        return top;
    }

    public AccountNotice withTop(Boolean top) {
        this.setTop(top);
        return this;
    }

    public void setTop(Boolean top) {
        this.top = top;
    }

    public Boolean getDel() {
        return del;
    }

    public AccountNotice withDel(Boolean del) {
        this.setDel(del);
        return this;
    }

    public void setDel(Boolean del) {
        this.del = del;
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
        sb.append(", looked=").append(looked);
        sb.append(", top=").append(top);
        sb.append(", del=").append(del);
        sb.append(", relativeImportance=").append(relativeImportance);
        sb.append("]");
        sb.append(", from super class ");
        sb.append(super.toString());
        return sb.toString();
    }
}