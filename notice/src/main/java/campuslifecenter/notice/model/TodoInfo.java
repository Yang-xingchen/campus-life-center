package campuslifecenter.notice.model;

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

    public TodoInfo setValue(String value) {
        this.value = value;
        return this;
    }

    public String getTitle() {
        return value;
    }

    public TodoInfo setTitle(String title) {
        this.value = title;
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
