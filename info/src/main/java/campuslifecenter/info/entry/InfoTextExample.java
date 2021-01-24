package campuslifecenter.info.entry;

import java.util.ArrayList;
import java.util.List;

public class InfoTextExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public InfoTextExample() {
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

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andRegularIsNull() {
            addCriterion("regular is null");
            return (Criteria) this;
        }

        public Criteria andRegularIsNotNull() {
            addCriterion("regular is not null");
            return (Criteria) this;
        }

        public Criteria andRegularEqualTo(String value) {
            addCriterion("regular =", value, "regular");
            return (Criteria) this;
        }

        public Criteria andRegularNotEqualTo(String value) {
            addCriterion("regular <>", value, "regular");
            return (Criteria) this;
        }

        public Criteria andRegularGreaterThan(String value) {
            addCriterion("regular >", value, "regular");
            return (Criteria) this;
        }

        public Criteria andRegularGreaterThanOrEqualTo(String value) {
            addCriterion("regular >=", value, "regular");
            return (Criteria) this;
        }

        public Criteria andRegularLessThan(String value) {
            addCriterion("regular <", value, "regular");
            return (Criteria) this;
        }

        public Criteria andRegularLessThanOrEqualTo(String value) {
            addCriterion("regular <=", value, "regular");
            return (Criteria) this;
        }

        public Criteria andRegularLike(String value) {
            addCriterion("regular like", value, "regular");
            return (Criteria) this;
        }

        public Criteria andRegularNotLike(String value) {
            addCriterion("regular not like", value, "regular");
            return (Criteria) this;
        }

        public Criteria andRegularIn(List<String> values) {
            addCriterion("regular in", values, "regular");
            return (Criteria) this;
        }

        public Criteria andRegularNotIn(List<String> values) {
            addCriterion("regular not in", values, "regular");
            return (Criteria) this;
        }

        public Criteria andRegularBetween(String value1, String value2) {
            addCriterion("regular between", value1, value2, "regular");
            return (Criteria) this;
        }

        public Criteria andRegularNotBetween(String value1, String value2) {
            addCriterion("regular not between", value1, value2, "regular");
            return (Criteria) this;
        }

        public Criteria andSampleIsNull() {
            addCriterion("sample is null");
            return (Criteria) this;
        }

        public Criteria andSampleIsNotNull() {
            addCriterion("sample is not null");
            return (Criteria) this;
        }

        public Criteria andSampleEqualTo(String value) {
            addCriterion("sample =", value, "sample");
            return (Criteria) this;
        }

        public Criteria andSampleNotEqualTo(String value) {
            addCriterion("sample <>", value, "sample");
            return (Criteria) this;
        }

        public Criteria andSampleGreaterThan(String value) {
            addCriterion("sample >", value, "sample");
            return (Criteria) this;
        }

        public Criteria andSampleGreaterThanOrEqualTo(String value) {
            addCriterion("sample >=", value, "sample");
            return (Criteria) this;
        }

        public Criteria andSampleLessThan(String value) {
            addCriterion("sample <", value, "sample");
            return (Criteria) this;
        }

        public Criteria andSampleLessThanOrEqualTo(String value) {
            addCriterion("sample <=", value, "sample");
            return (Criteria) this;
        }

        public Criteria andSampleLike(String value) {
            addCriterion("sample like", value, "sample");
            return (Criteria) this;
        }

        public Criteria andSampleNotLike(String value) {
            addCriterion("sample not like", value, "sample");
            return (Criteria) this;
        }

        public Criteria andSampleIn(List<String> values) {
            addCriterion("sample in", values, "sample");
            return (Criteria) this;
        }

        public Criteria andSampleNotIn(List<String> values) {
            addCriterion("sample not in", values, "sample");
            return (Criteria) this;
        }

        public Criteria andSampleBetween(String value1, String value2) {
            addCriterion("sample between", value1, value2, "sample");
            return (Criteria) this;
        }

        public Criteria andSampleNotBetween(String value1, String value2) {
            addCriterion("sample not between", value1, value2, "sample");
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