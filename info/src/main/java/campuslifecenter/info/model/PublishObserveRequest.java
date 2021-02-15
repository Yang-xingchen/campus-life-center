package campuslifecenter.info.model;

import java.io.Serializable;

public class PublishObserveRequest implements Serializable {

    private int type = 3;
    private String aid;

    private Long iid;
    private String text;

    public int getType() {
        return type;
    }

    public PublishObserveRequest setType(int type) {
        this.type = type;
        return this;
    }

    public String getAid() {
        return aid;
    }

    public PublishObserveRequest setAid(String aid) {
        this.aid = aid;
        return this;
    }

    public Long getIid() {
        return iid;
    }

    public PublishObserveRequest setIid(Long iid) {
        this.iid = iid;
        return this;
    }

    public String getText() {
        return text;
    }

    public PublishObserveRequest setText(String text) {
        this.text = text;
        return this;
    }
}
