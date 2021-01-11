package campuslifecenter.info.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class AccountInfo extends AccountInfoKey implements Serializable {
    private String text;

    @ApiModelProperty(value = "是否加密")
    private Boolean code;

    @ApiModelProperty(value = "公开度: 0.公开; 1.统计; 2.管理员; 3.私密")
    private Integer visibility;

    private static final long serialVersionUID = 1L;

    public String getText() {
        return text;
    }

    public AccountInfo withText(String text) {
        this.setText(text);
        return this;
    }

    public void setText(String text) {
        this.text = text == null ? null : text.trim();
    }

    public Boolean getCode() {
        return code;
    }

    public AccountInfo withCode(Boolean code) {
        this.setCode(code);
        return this;
    }

    public void setCode(Boolean code) {
        this.code = code;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public AccountInfo withVisibility(Integer visibility) {
        this.setVisibility(visibility);
        return this;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", text=").append(text);
        sb.append(", code=").append(code);
        sb.append(", visibility=").append(visibility);
        sb.append("]");
        sb.append(", from super class ");
        sb.append(super.toString());
        return sb.toString();
    }
}