package campuslifecenter.notice.entry;

import java.util.ArrayList;
import java.util.List;

public class NoticeConditionExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public NoticeConditionExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andNidIsNull() {
            addCriterion("nid is null");
            return (Criteria) this;
        }

        public Criteria andNidIsNotNull() {
            addCriterion("nid is not null");
            return (Criteria) this;
        }

        public Criteria andNidEqualTo(Integer value) {
            addCriterion("nid =", value, "nid");
            return (Criteria) this;
        }

        public Criteria andNidNotEqualTo(Integer value) {
            addCriterion("nid <>", value, "nid");
            return (Criteria) this;
        }

        public Criteria andNidGreaterThan(Integer value) {
            addCriterion("nid >", value, "nid");
            return (Criteria) this;
        }

        public Criteria andNidGreaterThanOrEqualTo(Integer value) {
            addCriterion("nid >=", value, "nid");
            return (Criteria) this;
        }

        public Criteria andNidLessThan(Integer value) {
            addCriterion("nid <", value, "nid");
            return (Criteria) this;
        }

        public Criteria andNidLessThanOrEqualTo(Integer value) {
            addCriterion("nid <=", value, "nid");
            return (Criteria) this;
        }

        public Criteria andNidIn(List<Integer> values) {
            addCriterion("nid in", values, "nid");
            return (Criteria) this;
        }

        public Criteria andNidNotIn(List<Integer> values) {
            addCriterion("nid not in", values, "nid");
            return (Criteria) this;
        }

        public Criteria andNidBetween(Integer value1, Integer value2) {
            addCriterion("nid between", value1, value2, "nid");
            return (Criteria) this;
        }

        public Criteria andNidNotBetween(Integer value1, Integer value2) {
            addCriterion("nid not between", value1, value2, "nid");
            return (Criteria) this;
        }

        public Criteria andConditionTypeIsNull() {
            addCriterion("condition_type is null");
            return (Criteria) this;
        }

        public Criteria andConditionTypeIsNotNull() {
            addCriterion("condition_type is not null");
            return (Criteria) this;
        }

        public Criteria andConditionTypeEqualTo(Byte value) {
            addCriterion("condition_type =", value, "conditionType");
            return (Criteria) this;
        }

        public Criteria andConditionTypeNotEqualTo(Byte value) {
            addCriterion("condition_type <>", value, "conditionType");
            return (Criteria) this;
        }

        public Criteria andConditionTypeGreaterThan(Byte value) {
            addCriterion("condition_type >", value, "conditionType");
            return (Criteria) this;
        }

        public Criteria andConditionTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("condition_type >=", value, "conditionType");
            return (Criteria) this;
        }

        public Criteria andConditionTypeLessThan(Byte value) {
            addCriterion("condition_type <", value, "conditionType");
            return (Criteria) this;
        }

        public Criteria andConditionTypeLessThanOrEqualTo(Byte value) {
            addCriterion("condition_type <=", value, "conditionType");
            return (Criteria) this;
        }

        public Criteria andConditionTypeIn(List<Byte> values) {
            addCriterion("condition_type in", values, "conditionType");
            return (Criteria) this;
        }

        public Criteria andConditionTypeNotIn(List<Byte> values) {
            addCriterion("condition_type not in", values, "conditionType");
            return (Criteria) this;
        }

        public Criteria andConditionTypeBetween(Byte value1, Byte value2) {
            addCriterion("condition_type between", value1, value2, "conditionType");
            return (Criteria) this;
        }

        public Criteria andConditionTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("condition_type not between", value1, value2, "conditionType");
            return (Criteria) this;
        }

        public Criteria andConditionKeyIsNull() {
            addCriterion("condition_key is null");
            return (Criteria) this;
        }

        public Criteria andConditionKeyIsNotNull() {
            addCriterion("condition_key is not null");
            return (Criteria) this;
        }

        public Criteria andConditionKeyEqualTo(String value) {
            addCriterion("condition_key =", value, "conditionKey");
            return (Criteria) this;
        }

        public Criteria andConditionKeyNotEqualTo(String value) {
            addCriterion("condition_key <>", value, "conditionKey");
            return (Criteria) this;
        }

        public Criteria andConditionKeyGreaterThan(String value) {
            addCriterion("condition_key >", value, "conditionKey");
            return (Criteria) this;
        }

        public Criteria andConditionKeyGreaterThanOrEqualTo(String value) {
            addCriterion("condition_key >=", value, "conditionKey");
            return (Criteria) this;
        }

        public Criteria andConditionKeyLessThan(String value) {
            addCriterion("condition_key <", value, "conditionKey");
            return (Criteria) this;
        }

        public Criteria andConditionKeyLessThanOrEqualTo(String value) {
            addCriterion("condition_key <=", value, "conditionKey");
            return (Criteria) this;
        }

        public Criteria andConditionKeyLike(String value) {
            addCriterion("condition_key like", value, "conditionKey");
            return (Criteria) this;
        }

        public Criteria andConditionKeyNotLike(String value) {
            addCriterion("condition_key not like", value, "conditionKey");
            return (Criteria) this;
        }

        public Criteria andConditionKeyIn(List<String> values) {
            addCriterion("condition_key in", values, "conditionKey");
            return (Criteria) this;
        }

        public Criteria andConditionKeyNotIn(List<String> values) {
            addCriterion("condition_key not in", values, "conditionKey");
            return (Criteria) this;
        }

        public Criteria andConditionKeyBetween(String value1, String value2) {
            addCriterion("condition_key between", value1, value2, "conditionKey");
            return (Criteria) this;
        }

        public Criteria andConditionKeyNotBetween(String value1, String value2) {
            addCriterion("condition_key not between", value1, value2, "conditionKey");
            return (Criteria) this;
        }

        public Criteria andConditionOperationIsNull() {
            addCriterion("condition_operation is null");
            return (Criteria) this;
        }

        public Criteria andConditionOperationIsNotNull() {
            addCriterion("condition_operation is not null");
            return (Criteria) this;
        }

        public Criteria andConditionOperationEqualTo(Byte value) {
            addCriterion("condition_operation =", value, "conditionOperation");
            return (Criteria) this;
        }

        public Criteria andConditionOperationNotEqualTo(Byte value) {
            addCriterion("condition_operation <>", value, "conditionOperation");
            return (Criteria) this;
        }

        public Criteria andConditionOperationGreaterThan(Byte value) {
            addCriterion("condition_operation >", value, "conditionOperation");
            return (Criteria) this;
        }

        public Criteria andConditionOperationGreaterThanOrEqualTo(Byte value) {
            addCriterion("condition_operation >=", value, "conditionOperation");
            return (Criteria) this;
        }

        public Criteria andConditionOperationLessThan(Byte value) {
            addCriterion("condition_operation <", value, "conditionOperation");
            return (Criteria) this;
        }

        public Criteria andConditionOperationLessThanOrEqualTo(Byte value) {
            addCriterion("condition_operation <=", value, "conditionOperation");
            return (Criteria) this;
        }

        public Criteria andConditionOperationIn(List<Byte> values) {
            addCriterion("condition_operation in", values, "conditionOperation");
            return (Criteria) this;
        }

        public Criteria andConditionOperationNotIn(List<Byte> values) {
            addCriterion("condition_operation not in", values, "conditionOperation");
            return (Criteria) this;
        }

        public Criteria andConditionOperationBetween(Byte value1, Byte value2) {
            addCriterion("condition_operation between", value1, value2, "conditionOperation");
            return (Criteria) this;
        }

        public Criteria andConditionOperationNotBetween(Byte value1, Byte value2) {
            addCriterion("condition_operation not between", value1, value2, "conditionOperation");
            return (Criteria) this;
        }

        public Criteria andConditionValueIsNull() {
            addCriterion("condition_value is null");
            return (Criteria) this;
        }

        public Criteria andConditionValueIsNotNull() {
            addCriterion("condition_value is not null");
            return (Criteria) this;
        }

        public Criteria andConditionValueEqualTo(String value) {
            addCriterion("condition_value =", value, "conditionValue");
            return (Criteria) this;
        }

        public Criteria andConditionValueNotEqualTo(String value) {
            addCriterion("condition_value <>", value, "conditionValue");
            return (Criteria) this;
        }

        public Criteria andConditionValueGreaterThan(String value) {
            addCriterion("condition_value >", value, "conditionValue");
            return (Criteria) this;
        }

        public Criteria andConditionValueGreaterThanOrEqualTo(String value) {
            addCriterion("condition_value >=", value, "conditionValue");
            return (Criteria) this;
        }

        public Criteria andConditionValueLessThan(String value) {
            addCriterion("condition_value <", value, "conditionValue");
            return (Criteria) this;
        }

        public Criteria andConditionValueLessThanOrEqualTo(String value) {
            addCriterion("condition_value <=", value, "conditionValue");
            return (Criteria) this;
        }

        public Criteria andConditionValueLike(String value) {
            addCriterion("condition_value like", value, "conditionValue");
            return (Criteria) this;
        }

        public Criteria andConditionValueNotLike(String value) {
            addCriterion("condition_value not like", value, "conditionValue");
            return (Criteria) this;
        }

        public Criteria andConditionValueIn(List<String> values) {
            addCriterion("condition_value in", values, "conditionValue");
            return (Criteria) this;
        }

        public Criteria andConditionValueNotIn(List<String> values) {
            addCriterion("condition_value not in", values, "conditionValue");
            return (Criteria) this;
        }

        public Criteria andConditionValueBetween(String value1, String value2) {
            addCriterion("condition_value between", value1, value2, "conditionValue");
            return (Criteria) this;
        }

        public Criteria andConditionValueNotBetween(String value1, String value2) {
            addCriterion("condition_value not between", value1, value2, "conditionValue");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}