package campuslifecenter.notice.entry;

import java.util.ArrayList;
import java.util.List;

public class NoticeButtonExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public NoticeButtonExample() {
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

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andUnfinishedValueIsNull() {
            addCriterion("unfinished_value is null");
            return (Criteria) this;
        }

        public Criteria andUnfinishedValueIsNotNull() {
            addCriterion("unfinished_value is not null");
            return (Criteria) this;
        }

        public Criteria andUnfinishedValueEqualTo(String value) {
            addCriterion("unfinished_value =", value, "unfinishedValue");
            return (Criteria) this;
        }

        public Criteria andUnfinishedValueNotEqualTo(String value) {
            addCriterion("unfinished_value <>", value, "unfinishedValue");
            return (Criteria) this;
        }

        public Criteria andUnfinishedValueGreaterThan(String value) {
            addCriterion("unfinished_value >", value, "unfinishedValue");
            return (Criteria) this;
        }

        public Criteria andUnfinishedValueGreaterThanOrEqualTo(String value) {
            addCriterion("unfinished_value >=", value, "unfinishedValue");
            return (Criteria) this;
        }

        public Criteria andUnfinishedValueLessThan(String value) {
            addCriterion("unfinished_value <", value, "unfinishedValue");
            return (Criteria) this;
        }

        public Criteria andUnfinishedValueLessThanOrEqualTo(String value) {
            addCriterion("unfinished_value <=", value, "unfinishedValue");
            return (Criteria) this;
        }

        public Criteria andUnfinishedValueLike(String value) {
            addCriterion("unfinished_value like", value, "unfinishedValue");
            return (Criteria) this;
        }

        public Criteria andUnfinishedValueNotLike(String value) {
            addCriterion("unfinished_value not like", value, "unfinishedValue");
            return (Criteria) this;
        }

        public Criteria andUnfinishedValueIn(List<String> values) {
            addCriterion("unfinished_value in", values, "unfinishedValue");
            return (Criteria) this;
        }

        public Criteria andUnfinishedValueNotIn(List<String> values) {
            addCriterion("unfinished_value not in", values, "unfinishedValue");
            return (Criteria) this;
        }

        public Criteria andUnfinishedValueBetween(String value1, String value2) {
            addCriterion("unfinished_value between", value1, value2, "unfinishedValue");
            return (Criteria) this;
        }

        public Criteria andUnfinishedValueNotBetween(String value1, String value2) {
            addCriterion("unfinished_value not between", value1, value2, "unfinishedValue");
            return (Criteria) this;
        }

        public Criteria andFinishValueIsNull() {
            addCriterion("finish_value is null");
            return (Criteria) this;
        }

        public Criteria andFinishValueIsNotNull() {
            addCriterion("finish_value is not null");
            return (Criteria) this;
        }

        public Criteria andFinishValueEqualTo(String value) {
            addCriterion("finish_value =", value, "finishValue");
            return (Criteria) this;
        }

        public Criteria andFinishValueNotEqualTo(String value) {
            addCriterion("finish_value <>", value, "finishValue");
            return (Criteria) this;
        }

        public Criteria andFinishValueGreaterThan(String value) {
            addCriterion("finish_value >", value, "finishValue");
            return (Criteria) this;
        }

        public Criteria andFinishValueGreaterThanOrEqualTo(String value) {
            addCriterion("finish_value >=", value, "finishValue");
            return (Criteria) this;
        }

        public Criteria andFinishValueLessThan(String value) {
            addCriterion("finish_value <", value, "finishValue");
            return (Criteria) this;
        }

        public Criteria andFinishValueLessThanOrEqualTo(String value) {
            addCriterion("finish_value <=", value, "finishValue");
            return (Criteria) this;
        }

        public Criteria andFinishValueLike(String value) {
            addCriterion("finish_value like", value, "finishValue");
            return (Criteria) this;
        }

        public Criteria andFinishValueNotLike(String value) {
            addCriterion("finish_value not like", value, "finishValue");
            return (Criteria) this;
        }

        public Criteria andFinishValueIn(List<String> values) {
            addCriterion("finish_value in", values, "finishValue");
            return (Criteria) this;
        }

        public Criteria andFinishValueNotIn(List<String> values) {
            addCriterion("finish_value not in", values, "finishValue");
            return (Criteria) this;
        }

        public Criteria andFinishValueBetween(String value1, String value2) {
            addCriterion("finish_value between", value1, value2, "finishValue");
            return (Criteria) this;
        }

        public Criteria andFinishValueNotBetween(String value1, String value2) {
            addCriterion("finish_value not between", value1, value2, "finishValue");
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