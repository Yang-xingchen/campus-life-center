package campuslifecenter.info.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class InfoText implements Serializable {
    private Long id;

    @ApiModelProperty(value = "类型: 0.文本; 1.数字; 2.正则")
    private Integer type;

    private String regular;

    @ApiModelProperty(value = "示例")
    private String sample;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public InfoText withId(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public InfoText withType(Integer type) {
        this.setType(type);
        return this;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getRegular() {
        return regular;
    }

    public InfoText withRegular(String regular) {
        this.setRegular(regular);
        return this;
    }

    public void setRegular(String regular) {
        this.regular = regular == null ? null : regular.trim();
    }

    public String getSample() {
        return sample;
    }

    public InfoText withSample(String sample) {
        this.setSample(sample);
        return this;
    }

    public void setSample(String sample) {
        this.sample = sample == null ? null : sample.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", type=").append(type);
        sb.append(", regular=").append(regular);
        sb.append(", sample=").append(sample);
        sb.append("]");
        return sb.toString();
    }
}