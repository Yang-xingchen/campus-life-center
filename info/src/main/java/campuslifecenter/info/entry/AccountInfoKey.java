package campuslifecenter.info.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class AccountInfoKey implements Serializable {
    private String aid;

    private Long id;

    private static final long serialVersionUID = 1L;

    public String getAid() {
        return aid;
    }

    public AccountInfoKey withAid(String aid) {
        this.setAid(aid);
        return this;
    }

    public void setAid(String aid) {
        this.aid = aid == null ? null : aid.trim();
    }

    public Long getId() {
        return id;
    }

    public AccountInfoKey withId(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", aid=").append(aid);
        sb.append(", id=").append(id);
        sb.append("]");
        return sb.toString();
    }
}