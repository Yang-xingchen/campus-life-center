package campuslifecenter.notice.entry;

import java.util.ArrayList;
import java.util.List;

public class AccountNoticeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AccountNoticeExample() {
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

        public Criteria andReadIsNull() {
            addCriterion("read is null");
            return (Criteria) this;
        }

        public Criteria andReadIsNotNull() {
            addCriterion("read is not null");
            return (Criteria) this;
        }

        public Criteria andReadEqualTo(Byte value) {
            addCriterion("read =", value, "read");
            return (Criteria) this;
        }

        public Criteria andReadNotEqualTo(Byte value) {
            addCriterion("read <>", value, "read");
            return (Criteria) this;
        }

        public Criteria andReadGreaterThan(Byte value) {
            addCriterion("read >", value, "read");
            return (Criteria) this;
        }

        public Criteria andReadGreaterThanOrEqualTo(Byte value) {
            addCriterion("read >=", value, "read");
            return (Criteria) this;
        }

        public Criteria andReadLessThan(Byte value) {
            addCriterion("read <", value, "read");
            return (Criteria) this;
        }

        public Criteria andReadLessThanOrEqualTo(Byte value) {
            addCriterion("read <=", value, "read");
            return (Criteria) this;
        }

        public Criteria andReadIn(List<Byte> values) {
            addCriterion("read in", values, "read");
            return (Criteria) this;
        }

        public Criteria andReadNotIn(List<Byte> values) {
            addCriterion("read not in", values, "read");
            return (Criteria) this;
        }

        public Criteria andReadBetween(Byte value1, Byte value2) {
            addCriterion("read between", value1, value2, "read");
            return (Criteria) this;
        }

        public Criteria andReadNotBetween(Byte value1, Byte value2) {
            addCriterion("read not between", value1, value2, "read");
            return (Criteria) this;
        }

        public Criteria andTopIsNull() {
            addCriterion("top is null");
            return (Criteria) this;
        }

        public Criteria andTopIsNotNull() {
            addCriterion("top is not null");
            return (Criteria) this;
        }

        public Criteria andTopEqualTo(Byte value) {
            addCriterion("top =", value, "top");
            return (Criteria) this;
        }

        public Criteria andTopNotEqualTo(Byte value) {
            addCriterion("top <>", value, "top");
            return (Criteria) this;
        }

        public Criteria andTopGreaterThan(Byte value) {
            addCriterion("top >", value, "top");
            return (Criteria) this;
        }

        public Criteria andTopGreaterThanOrEqualTo(Byte value) {
            addCriterion("top >=", value, "top");
            return (Criteria) this;
        }

        public Criteria andTopLessThan(Byte value) {
            addCriterion("top <", value, "top");
            return (Criteria) this;
        }

        public Criteria andTopLessThanOrEqualTo(Byte value) {
            addCriterion("top <=", value, "top");
            return (Criteria) this;
        }

        public Criteria andTopIn(List<Byte> values) {
            addCriterion("top in", values, "top");
            return (Criteria) this;
        }

        public Criteria andTopNotIn(List<Byte> values) {
            addCriterion("top not in", values, "top");
            return (Criteria) this;
        }

        public Criteria andTopBetween(Byte value1, Byte value2) {
            addCriterion("top between", value1, value2, "top");
            return (Criteria) this;
        }

        public Criteria andTopNotBetween(Byte value1, Byte value2) {
            addCriterion("top not between", value1, value2, "top");
            return (Criteria) this;
        }

        public Criteria andDeleteIsNull() {
            addCriterion("delete is null");
            return (Criteria) this;
        }

        public Criteria andDeleteIsNotNull() {
            addCriterion("delete is not null");
            return (Criteria) this;
        }

        public Criteria andDeleteEqualTo(Byte value) {
            addCriterion("delete =", value, "delete");
            return (Criteria) this;
        }

        public Criteria andDeleteNotEqualTo(Byte value) {
            addCriterion("delete <>", value, "delete");
            return (Criteria) this;
        }

        public Criteria andDeleteGreaterThan(Byte value) {
            addCriterion("delete >", value, "delete");
            return (Criteria) this;
        }

        public Criteria andDeleteGreaterThanOrEqualTo(Byte value) {
            addCriterion("delete >=", value, "delete");
            return (Criteria) this;
        }

        public Criteria andDeleteLessThan(Byte value) {
            addCriterion("delete <", value, "delete");
            return (Criteria) this;
        }

        public Criteria andDeleteLessThanOrEqualTo(Byte value) {
            addCriterion("delete <=", value, "delete");
            return (Criteria) this;
        }

        public Criteria andDeleteIn(List<Byte> values) {
            addCriterion("delete in", values, "delete");
            return (Criteria) this;
        }

        public Criteria andDeleteNotIn(List<Byte> values) {
            addCriterion("delete not in", values, "delete");
            return (Criteria) this;
        }

        public Criteria andDeleteBetween(Byte value1, Byte value2) {
            addCriterion("delete between", value1, value2, "delete");
            return (Criteria) this;
        }

        public Criteria andDeleteNotBetween(Byte value1, Byte value2) {
            addCriterion("delete not between", value1, value2, "delete");
            return (Criteria) this;
        }

        public Criteria andRelativeImportanceIsNull() {
            addCriterion("relative_importance is null");
            return (Criteria) this;
        }

        public Criteria andRelativeImportanceIsNotNull() {
            addCriterion("relative_importance is not null");
            return (Criteria) this;
        }

        public Criteria andRelativeImportanceEqualTo(Byte value) {
            addCriterion("relative_importance =", value, "relativeImportance");
            return (Criteria) this;
        }

        public Criteria andRelativeImportanceNotEqualTo(Byte value) {
            addCriterion("relative_importance <>", value, "relativeImportance");
            return (Criteria) this;
        }

        public Criteria andRelativeImportanceGreaterThan(Byte value) {
            addCriterion("relative_importance >", value, "relativeImportance");
            return (Criteria) this;
        }

        public Criteria andRelativeImportanceGreaterThanOrEqualTo(Byte value) {
            addCriterion("relative_importance >=", value, "relativeImportance");
            return (Criteria) this;
        }

        public Criteria andRelativeImportanceLessThan(Byte value) {
            addCriterion("relative_importance <", value, "relativeImportance");
            return (Criteria) this;
        }

        public Criteria andRelativeImportanceLessThanOrEqualTo(Byte value) {
            addCriterion("relative_importance <=", value, "relativeImportance");
            return (Criteria) this;
        }

        public Criteria andRelativeImportanceIn(List<Byte> values) {
            addCriterion("relative_importance in", values, "relativeImportance");
            return (Criteria) this;
        }

        public Criteria andRelativeImportanceNotIn(List<Byte> values) {
            addCriterion("relative_importance not in", values, "relativeImportance");
            return (Criteria) this;
        }

        public Criteria andRelativeImportanceBetween(Byte value1, Byte value2) {
            addCriterion("relative_importance between", value1, value2, "relativeImportance");
            return (Criteria) this;
        }

        public Criteria andRelativeImportanceNotBetween(Byte value1, Byte value2) {
            addCriterion("relative_importance not between", value1, value2, "relativeImportance");
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