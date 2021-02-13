package campuslifecenter.info.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class AccountSaveInfo extends AccountSaveInfoKey implements Serializable {
    private String content;

    @ApiModelProperty(value = "是否加密")
    private Boolean code;

    @ApiModelProperty(value = "公开度: 0.公开; 1.统计; 2.管理员; 3.私密")
    private Integer visibility;

    private static final long serialVersionUID = 1L;

    public String getContent() {
        return content;
    }

    public AccountSaveInfo withContent(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Boolean getCode() {
        return code;
    }

    public AccountSaveInfo withCode(Boolean code) {
        this.setCode(code);
        return this;
    }

    public void setCode(Boolean code) {
        this.code = code;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public AccountSaveInfo withVisibility(Integer visibility) {
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
        sb.append(", content=").append(content);
        sb.append(", code=").append(code);
        sb.append(", visibility=").append(visibility);
        sb.append("]");
        sb.append(", from super class ");
        sb.append(super.toString());
        return sb.toString();
    }
}