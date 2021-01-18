package campuslifecenter.notice.model;

import com.fasterxml.jackson.annotation.JsonSetter;

import java.io.Serializable;

public class TodoInfo implements Serializable {
    private Long id;
    private String value;
    private String source;

    public Long getId() {
        return id;
    }

    public TodoInfo setId(Long id) {
        this.id = id;
        return this;
    }

    public String getValue() {
        return value;
    }

    @JsonSetter("title")
    public TodoInfo setValue(String value) {
        this.value = value;
        return this;
    }

    public String getSource() {
        return source;
    }

    public TodoInfo setSource(String source) {
        this.source = source;
        return this;
    }
}
