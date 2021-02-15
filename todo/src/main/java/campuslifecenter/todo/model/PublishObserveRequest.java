package campuslifecenter.todo.model;

import java.io.Serializable;

public class PublishObserveRequest implements Serializable {

    private int type = 2;
    private String aid;

    private Long tid;
    private Boolean finish;

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

}
