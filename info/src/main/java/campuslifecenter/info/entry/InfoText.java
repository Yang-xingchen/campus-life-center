package campuslifecenter.info.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class InfoText implements Serializable {
    private Long id;

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
        sb.append(", sample=").append(sample);
        sb.append("]");
        return sb.toString();
    }
}