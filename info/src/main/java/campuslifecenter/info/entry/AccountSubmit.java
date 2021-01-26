package campuslifecenter.info.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class AccountSubmit extends AccountSubmitKey implements Serializable {
    @ApiModelProperty(value = "提交的内容")
    private String text;

    private static final long serialVersionUID = 1L;

    public String getText() {
        return text;
    }

    public AccountSubmit withText(String text) {
        this.setText(text);
        return this;
    }

    public void setText(String text) {
        this.text = text == null ? null : text.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", text=").append(text);
        sb.append("]");
        sb.append(", from super class ");
        sb.append(super.toString());
        return sb.toString();
    }
}