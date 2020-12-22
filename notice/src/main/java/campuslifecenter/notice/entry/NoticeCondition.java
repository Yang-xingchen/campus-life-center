package campuslifecenter.notice.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class NoticeCondition extends NoticeConditionKey implements Serializable {
    private String conditionKey;

    private Integer conditionOperation;

    private String conditionValue;

    private static final long serialVersionUID = 1L;

    public String getConditionKey() {
        return conditionKey;
    }

    public NoticeCondition withConditionKey(String conditionKey) {
        this.setConditionKey(conditionKey);
        return this;
    }

    public void setConditionKey(String conditionKey) {
        this.conditionKey = conditionKey == null ? null : conditionKey.trim();
    }

    public Integer getConditionOperation() {
        return conditionOperation;
    }

    public NoticeCondition withConditionOperation(Integer conditionOperation) {
        this.setConditionOperation(conditionOperation);
        return this;
    }

    public void setConditionOperation(Integer conditionOperation) {
        this.conditionOperation = conditionOperation;
    }

    public String getConditionValue() {
        return conditionValue;
    }

    public NoticeCondition withConditionValue(String conditionValue) {
        this.setConditionValue(conditionValue);
        return this;
    }

    public void setConditionValue(String conditionValue) {
        this.conditionValue = conditionValue == null ? null : conditionValue.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", conditionKey=").append(conditionKey);
        sb.append(", conditionOperation=").append(conditionOperation);
        sb.append(", conditionValue=").append(conditionValue);
        sb.append("]");
        return sb.toString();
    }
}