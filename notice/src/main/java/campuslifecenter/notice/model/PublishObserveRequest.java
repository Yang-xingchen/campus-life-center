package campuslifecenter.notice.model;

import java.io.Serializable;

public class PublishObserveRequest implements Serializable {

    public static final int ORGANIZATION = 1;
    public static final int TODO = 2;
    public static final int INFO = 3;

    private int type;
    private String aid;

    private Integer oid;
    private Boolean belong;
    private Boolean subscribe;

    private Long tid;
    private Boolean finish;

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

    public Integer getOid() {
        return oid;
    }

    public PublishObserveRequest setOid(Integer oid) {
        this.oid = oid;
        return this;
    }

    public Boolean getBelong() {
        return belong;
    }

    public PublishObserveRequest setBelong(Boolean belong) {
        this.belong = belong;
        return this;
    }

    public Boolean getSubscribe() {
        return subscribe;
    }

    public PublishObserveRequest setSubscribe(Boolean subscribe) {
        this.subscribe = subscribe;
        return this;
    }

    public Long getTid() {
        return tid;
    }

    public PublishObserveRequest setTid(Long tid) {
        this.tid = tid;
        return this;
    }

    public Boolean getFinish() {
        return finish;
    }

    public PublishObserveRequest setFinish(Boolean finish) {
        this.finish = finish;
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
