package campuslifecenter.info.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class Info implements Serializable {
    private Long id;

    private String name;

    @ApiModelProperty(value = ": 0.; 1./; 2.")
    private Integer type;

    private String persistentSource;

    @ApiModelProperty(value = ": 0.; 1.; 2.; 3.")
    private Integer defaultVisibility;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public Info withId(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Info withName(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getType() {
        return type;
    }

    public Info withType(Integer type) {
        this.setType(type);
        return this;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPersistentSource() {
        return persistentSource;
    }

    public Info withPersistentSource(String persistentSource) {
        this.setPersistentSource(persistentSource);
        return this;
    }

    public void setPersistentSource(String persistentSource) {
        this.persistentSource = persistentSource == null ? null : persistentSource.trim();
    }

    public Integer getDefaultVisibility() {
        return defaultVisibility;
    }

    public Info withDefaultVisibility(Integer defaultVisibility) {
        this.setDefaultVisibility(defaultVisibility);
        return this;
    }

    public void setDefaultVisibility(Integer defaultVisibility) {
        this.defaultVisibility = defaultVisibility;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", type=").append(type);
        sb.append(", persistentSource=").append(persistentSource);
        sb.append(", defaultVisibility=").append(defaultVisibility);
        sb.append("]");
        return sb.toString();
    }
}