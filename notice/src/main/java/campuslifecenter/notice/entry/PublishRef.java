package campuslifecenter.notice.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class PublishRef extends PublishRefKey implements Serializable {
    @ApiModelProperty(value = "是否完成")
    private Boolean finish;

    private static final long serialVersionUID = 1L;

    public Boolean getFinish() {
        return finish;
    }

    public PublishRef withFinish(Boolean finish) {
        this.setFinish(finish);
        return this;
    }

    public void setFinish(Boolean finish) {
        this.finish = finish;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", finish=").append(finish);
        sb.append("]");
        sb.append(", from super class ");
        sb.append(super.toString());
        return sb.toString();
    }
}