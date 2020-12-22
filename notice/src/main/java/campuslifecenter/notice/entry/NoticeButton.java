package campuslifecenter.notice.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class NoticeButton implements Serializable {
    @ApiModelProperty(value = "id")
    private Long id;

    private String unfinishedValue;

    private String finishValue;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public NoticeButton withId(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUnfinishedValue() {
        return unfinishedValue;
    }

    public NoticeButton withUnfinishedValue(String unfinishedValue) {
        this.setUnfinishedValue(unfinishedValue);
        return this;
    }

    public void setUnfinishedValue(String unfinishedValue) {
        this.unfinishedValue = unfinishedValue == null ? null : unfinishedValue.trim();
    }

    public String getFinishValue() {
        return finishValue;
    }

    public NoticeButton withFinishValue(String finishValue) {
        this.setFinishValue(finishValue);
        return this;
    }

    public void setFinishValue(String finishValue) {
        this.finishValue = finishValue == null ? null : finishValue.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", unfinishedValue=").append(unfinishedValue);
        sb.append(", finishValue=").append(finishValue);
        sb.append("]");
        return sb.toString();
    }
}