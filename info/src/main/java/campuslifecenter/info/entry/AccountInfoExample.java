package campuslifecenter.info.entry;

import java.util.ArrayList;
import java.util.List;

public class AccountInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AccountInfoExample() {
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

        public Criteria andAidIsNull() {
            addCriterion("aid is null");
            return (Criteria) this;
        }

        public Criteria andAidIsNotNull() {
            addCriterion("aid is not null");
            return (Criteria) this;
        }

        public Criteria andAidEqualTo(String value) {
            addCriterion("aid =", value, "aid");
            return (Criteria) this;
        }

        public Criteria andAidNotEqualTo(String value) {
            addCriterion("aid <>", value, "aid");
            return (Criteria) this;
        }

        public Criteria andAidGreaterThan(String value) {
            addCriterion("aid >", value, "aid");
            return (Criteria) this;
        }

        public Criteria andAidGreaterThanOrEqualTo(String value) {
            addCriterion("aid >=", value, "aid");
            return (Criteria) this;
        }

        public Criteria andAidLessThan(String value) {
            addCriterion("aid <", value, "aid");
            return (Criteria) this;
        }

        public Criteria andAidLessThanOrEqualTo(String value) {
            addCriterion("aid <=", value, "aid");
            return (Criteria) this;
        }

        public Criteria andAidLike(String value) {
            addCriterion("aid like", value, "aid");
            return (Criteria) this;
        }

        public Criteria andAidNotLike(String value) {
            addCriterion("aid not like", value, "aid");
            return (Criteria) this;
        }

        public Criteria andAidIn(List<String> values) {
            addCriterion("aid in", values, "aid");
            return (Criteria) this;
        }

        public Criteria andAidNotIn(List<String> values) {
            addCriterion("aid not in", values, "aid");
            return (Criteria) this;
        }

        public Criteria andAidBetween(String value1, String value2) {
            addCriterion("aid between", value1, value2, "aid");
            return (Criteria) this;
        }

        public Criteria andAidNotBetween(String value1, String value2) {
            addCriterion("aid not between", value1, value2, "aid");
            return (Criteria) this;
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

        public Criteria andMultipleIndexIsNull() {
            addCriterion("multiple_index is null");
            return (Criteria) this;
        }

        public Criteria andMultipleIndexIsNotNull() {
            addCriterion("multiple_index is not null");
            return (Criteria) this;
        }

        public Criteria andMultipleIndexEqualTo(Integer value) {
            addCriterion("multiple_index =", value, "multipleIndex");
            return (Criteria) this;
        }

        public Criteria andMultipleIndexNotEqualTo(Integer value) {
            addCriterion("multiple_index <>", value, "multipleIndex");
            return (Criteria) this;
        }

        public Criteria andMultipleIndexGreaterThan(Integer value) {
            addCriterion("multiple_index >", value, "multipleIndex");
            return (Criteria) this;
        }

        public Criteria andMultipleIndexGreaterThanOrEqualTo(Integer value) {
            addCriterion("multiple_index >=", value, "multipleIndex");
            return (Criteria) this;
        }

        public Criteria andMultipleIndexLessThan(Integer value) {
            addCriterion("multiple_index <", value, "multipleIndex");
            return (Criteria) this;
        }

        public Criteria andMultipleIndexLessThanOrEqualTo(Integer value) {
            addCriterion("multiple_index <=", value, "multipleIndex");
            return (Criteria) this;
        }

        public Criteria andMultipleIndexIn(List<Integer> values) {
            addCriterion("multiple_index in", values, "multipleIndex");
            return (Criteria) this;
        }

        public Criteria andMultipleIndexNotIn(List<Integer> values) {
            addCriterion("multiple_index not in", values, "multipleIndex");
            return (Criteria) this;
        }

        public Criteria andMultipleIndexBetween(Integer value1, Integer value2) {
            addCriterion("multiple_index between", value1, value2, "multipleIndex");
            return (Criteria) this;
        }

        public Criteria andMultipleIndexNotBetween(Integer value1, Integer value2) {
            addCriterion("multiple_index not between", value1, value2, "multipleIndex");
            return (Criteria) this;
        }

        public Criteria andTextIsNull() {
            addCriterion("text is null");
            return (Criteria) this;
        }

        public Criteria andTextIsNotNull() {
            addCriterion("text is not null");
            return (Criteria) this;
        }

        public Criteria andTextEqualTo(String value) {
            addCriterion("text =", value, "text");
            return (Criteria) this;
        }

        public Criteria andTextNotEqualTo(String value) {
            addCriterion("text <>", value, "text");
            return (Criteria) this;
        }

        public Criteria andTextGreaterThan(String value) {
            addCriterion("text >", value, "text");
            return (Criteria) this;
        }

        public Criteria andTextGreaterThanOrEqualTo(String value) {
            addCriterion("text >=", value, "text");
            return (Criteria) this;
        }

        public Criteria andTextLessThan(String value) {
            addCriterion("text <", value, "text");
            return (Criteria) this;
        }

        public Criteria andTextLessThanOrEqualTo(String value) {
            addCriterion("text <=", value, "text");
            return (Criteria) this;
        }

        public Criteria andTextLike(String value) {
            addCriterion("text like", value, "text");
            return (Criteria) this;
        }

        public Criteria andTextNotLike(String value) {
            addCriterion("text not like", value, "text");
            return (Criteria) this;
        }

        public Criteria andTextIn(List<String> values) {
            addCriterion("text in", values, "text");
            return (Criteria) this;
        }

        public Criteria andTextNotIn(List<String> values) {
            addCriterion("text not in", values, "text");
            return (Criteria) this;
        }

        public Criteria andTextBetween(String value1, String value2) {
            addCriterion("text between", value1, value2, "text");
            return (Criteria) this;
        }

        public Criteria andTextNotBetween(String value1, String value2) {
            addCriterion("text not between", value1, value2, "text");
            return (Criteria) this;
        }

        public Criteria andCodeIsNull() {
            addCriterion("code is null");
            return (Criteria) this;
        }

        public Criteria andCodeIsNotNull() {
            addCriterion("code is not null");
            return (Criteria) this;
        }

        public Criteria andCodeEqualTo(Boolean value) {
            addCriterion("code =", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotEqualTo(Boolean value) {
            addCriterion("code <>", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThan(Boolean value) {
            addCriterion("code >", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThanOrEqualTo(Boolean value) {
            addCriterion("code >=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThan(Boolean value) {
            addCriterion("code <", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThanOrEqualTo(Boolean value) {
            addCriterion("code <=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeIn(List<Boolean> values) {
            addCriterion("code in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotIn(List<Boolean> values) {
            addCriterion("code not in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeBetween(Boolean value1, Boolean value2) {
            addCriterion("code between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotBetween(Boolean value1, Boolean value2) {
            addCriterion("code not between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andVisibilityIsNull() {
            addCriterion("visibility is null");
            return (Criteria) this;
        }

        public Criteria andVisibilityIsNotNull() {
            addCriterion("visibility is not null");
            return (Criteria) this;
        }

        public Criteria andVisibilityEqualTo(Integer value) {
            addCriterion("visibility =", value, "visibility");
            return (Criteria) this;
        }

        public Criteria andVisibilityNotEqualTo(Integer value) {
            addCriterion("visibility <>", value, "visibility");
            return (Criteria) this;
        }

        public Criteria andVisibilityGreaterThan(Integer value) {
            addCriterion("visibility >", value, "visibility");
            return (Criteria) this;
        }

        public Criteria andVisibilityGreaterThanOrEqualTo(Integer value) {
            addCriterion("visibility >=", value, "visibility");
            return (Criteria) this;
        }

        public Criteria andVisibilityLessThan(Integer value) {
            addCriterion("visibility <", value, "visibility");
            return (Criteria) this;
        }

        public Criteria andVisibilityLessThanOrEqualTo(Integer value) {
            addCriterion("visibility <=", value, "visibility");
            return (Criteria) this;
        }

        public Criteria andVisibilityIn(List<Integer> values) {
            addCriterion("visibility in", values, "visibility");
            return (Criteria) this;
        }

        public Criteria andVisibilityNotIn(List<Integer> values) {
            addCriterion("visibility not in", values, "visibility");
            return (Criteria) this;
        }

        public Criteria andVisibilityBetween(Integer value1, Integer value2) {
            addCriterion("visibility between", value1, value2, "visibility");
            return (Criteria) this;
        }

        public Criteria andVisibilityNotBetween(Integer value1, Integer value2) {
            addCriterion("visibility not between", value1, value2, "visibility");
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