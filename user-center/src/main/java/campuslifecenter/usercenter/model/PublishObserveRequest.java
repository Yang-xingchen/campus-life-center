package campuslifecenter.usercenter.model;

import java.io.Serializable;

public class PublishObserveRequest implements Serializable {

    private int type = 1;
    private String aid;

    private Integer oid;
    private Boolean belong;

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

}
