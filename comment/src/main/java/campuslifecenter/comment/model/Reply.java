package campuslifecenter.comment.model;

import java.io.Serializable;

public class Reply implements Serializable {
    private String content;

    public String getContent() {
        return content;
    }

    public Reply setContent(String content) {
        this.content = content;
        return this;
    }
}
