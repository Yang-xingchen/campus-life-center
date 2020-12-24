package campuslifecenter.notice.model;

public enum  NoticeConditionType {

    BUTTON(0),
    INFO(1),
    ORGANIZATION(2);
    private int key;

    NoticeConditionType(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public NoticeConditionType setKey(int key) {
        this.key = key;
        return this;
    }
}
