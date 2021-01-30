package campuslifecenter.info.entry;

import java.util.ArrayList;
import java.util.List;

public class RefRootExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RefRootExample() {
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

        public Criteria andRefIsNull() {
            addCriterion("ref is null");
            return (Criteria) this;
        }

        public Criteria andRefIsNotNull() {
            addCriterion("ref is not null");
            return (Criteria) this;
        }

        public Criteria andRefEqualTo(String value) {
            addCriterion("ref =", value, "ref");
            return (Criteria) this;
        }

        public Criteria andRefNotEqualTo(String value) {
            addCriterion("ref <>", value, "ref");
            return (Criteria) this;
        }

        public Criteria andRefGreaterThan(String value) {
            addCriterion("ref >", value, "ref");
            return (Criteria) this;
        }

        public Criteria andRefGreaterThanOrEqualTo(String value) {
            addCriterion("ref >=", value, "ref");
            return (Criteria) this;
        }

        public Criteria andRefLessThan(String value) {
            addCriterion("ref <", value, "ref");
            return (Criteria) this;
        }

        public Criteria andRefLessThanOrEqualTo(String value) {
            addCriterion("ref <=", value, "ref");
            return (Criteria) this;
        }

        public Criteria andRefLike(String value) {
            addCriterion("ref like", value, "ref");
            return (Criteria) this;
        }

        public Criteria andRefNotLike(String value) {
            addCriterion("ref not like", value, "ref");
            return (Criteria) this;
        }

        public Criteria andRefIn(List<String> values) {
            addCriterion("ref in", values, "ref");
            return (Criteria) this;
        }

        public Criteria andRefNotIn(List<String> values) {
            addCriterion("ref not in", values, "ref");
            return (Criteria) this;
        }

        public Criteria andRefBetween(String value1, String value2) {
            addCriterion("ref between", value1, value2, "ref");
            return (Criteria) this;
        }

        public Criteria andRefNotBetween(String value1, String value2) {
            addCriterion("ref not between", value1, value2, "ref");
            return (Criteria) this;
        }

        public Criteria andRootIsNull() {
            addCriterion("root is null");
            return (Criteria) this;
        }

        public Criteria andRootIsNotNull() {
            addCriterion("root is not null");
            return (Criteria) this;
        }

        public Criteria andRootEqualTo(Long value) {
            addCriterion("root =", value, "root");
            return (Criteria) this;
        }

        public Criteria andRootNotEqualTo(Long value) {
            addCriterion("root <>", value, "root");
            return (Criteria) this;
        }

        public Criteria andRootGreaterThan(Long value) {
            addCriterion("root >", value, "root");
            return (Criteria) this;
        }

        public Criteria andRootGreaterThanOrEqualTo(Long value) {
            addCriterion("root >=", value, "root");
            return (Criteria) this;
        }

        public Criteria andRootLessThan(Long value) {
            addCriterion("root <", value, "root");
            return (Criteria) this;
        }

        public Criteria andRootLessThanOrEqualTo(Long value) {
            addCriterion("root <=", value, "root");
            return (Criteria) this;
        }

        public Criteria andRootIn(List<Long> values) {
            addCriterion("root in", values, "root");
            return (Criteria) this;
        }

        public Criteria andRootNotIn(List<Long> values) {
            addCriterion("root not in", values, "root");
            return (Criteria) this;
        }

        public Criteria andRootBetween(Long value1, Long value2) {
            addCriterion("root between", value1, value2, "root");
            return (Criteria) this;
        }

        public Criteria andRootNotBetween(Long value1, Long value2) {
            addCriterion("root not between", value1, value2, "root");
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