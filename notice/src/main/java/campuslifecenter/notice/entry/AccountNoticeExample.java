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

        public Criteria andNidEqualTo(Long value) {
            addCriterion("nid =", value, "nid");
            return (Criteria) this;
        }

        public Criteria andNidNotEqualTo(Long value) {
            addCriterion("nid <>", value, "nid");
            return (Criteria) this;
        }

        public Criteria andNidGreaterThan(Long value) {
            addCriterion("nid >", value, "nid");
            return (Criteria) this;
        }

        public Criteria andNidGreaterThanOrEqualTo(Long value) {
            addCriterion("nid >=", value, "nid");
            return (Criteria) this;
        }

        public Criteria andNidLessThan(Long value) {
            addCriterion("nid <", value, "nid");
            return (Criteria) this;
        }

        public Criteria andNidLessThanOrEqualTo(Long value) {
            addCriterion("nid <=", value, "nid");
            return (Criteria) this;
        }

        public Criteria andNidIn(List<Long> values) {
            addCriterion("nid in", values, "nid");
            return (Criteria) this;
        }

        public Criteria andNidNotIn(List<Long> values) {
            addCriterion("nid not in", values, "nid");
            return (Criteria) this;
        }

        public Criteria andNidBetween(Long value1, Long value2) {
            addCriterion("nid between", value1, value2, "nid");
            return (Criteria) this;
        }

        public Criteria andNidNotBetween(Long value1, Long value2) {
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

        public Criteria andLookedIsNull() {
            addCriterion("looked is null");
            return (Criteria) this;
        }

        public Criteria andLookedIsNotNull() {
            addCriterion("looked is not null");
            return (Criteria) this;
        }

        public Criteria andLookedEqualTo(Boolean value) {
            addCriterion("looked =", value, "looked");
            return (Criteria) this;
        }

        public Criteria andLookedNotEqualTo(Boolean value) {
            addCriterion("looked <>", value, "looked");
            return (Criteria) this;
        }

        public Criteria andLookedGreaterThan(Boolean value) {
            addCriterion("looked >", value, "looked");
            return (Criteria) this;
        }

        public Criteria andLookedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("looked >=", value, "looked");
            return (Criteria) this;
        }

        public Criteria andLookedLessThan(Boolean value) {
            addCriterion("looked <", value, "looked");
            return (Criteria) this;
        }

        public Criteria andLookedLessThanOrEqualTo(Boolean value) {
            addCriterion("looked <=", value, "looked");
            return (Criteria) this;
        }

        public Criteria andLookedIn(List<Boolean> values) {
            addCriterion("looked in", values, "looked");
            return (Criteria) this;
        }

        public Criteria andLookedNotIn(List<Boolean> values) {
            addCriterion("looked not in", values, "looked");
            return (Criteria) this;
        }

        public Criteria andLookedBetween(Boolean value1, Boolean value2) {
            addCriterion("looked between", value1, value2, "looked");
            return (Criteria) this;
        }

        public Criteria andLookedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("looked not between", value1, value2, "looked");
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

        public Criteria andTopEqualTo(Boolean value) {
            addCriterion("top =", value, "top");
            return (Criteria) this;
        }

        public Criteria andTopNotEqualTo(Boolean value) {
            addCriterion("top <>", value, "top");
            return (Criteria) this;
        }

        public Criteria andTopGreaterThan(Boolean value) {
            addCriterion("top >", value, "top");
            return (Criteria) this;
        }

        public Criteria andTopGreaterThanOrEqualTo(Boolean value) {
            addCriterion("top >=", value, "top");
            return (Criteria) this;
        }

        public Criteria andTopLessThan(Boolean value) {
            addCriterion("top <", value, "top");
            return (Criteria) this;
        }

        public Criteria andTopLessThanOrEqualTo(Boolean value) {
            addCriterion("top <=", value, "top");
            return (Criteria) this;
        }

        public Criteria andTopIn(List<Boolean> values) {
            addCriterion("top in", values, "top");
            return (Criteria) this;
        }

        public Criteria andTopNotIn(List<Boolean> values) {
            addCriterion("top not in", values, "top");
            return (Criteria) this;
        }

        public Criteria andTopBetween(Boolean value1, Boolean value2) {
            addCriterion("top between", value1, value2, "top");
            return (Criteria) this;
        }

        public Criteria andTopNotBetween(Boolean value1, Boolean value2) {
            addCriterion("top not between", value1, value2, "top");
            return (Criteria) this;
        }

        public Criteria andDelIsNull() {
            addCriterion("del is null");
            return (Criteria) this;
        }

        public Criteria andDelIsNotNull() {
            addCriterion("del is not null");
            return (Criteria) this;
        }

        public Criteria andDelEqualTo(Boolean value) {
            addCriterion("del =", value, "del");
            return (Criteria) this;
        }

        public Criteria andDelNotEqualTo(Boolean value) {
            addCriterion("del <>", value, "del");
            return (Criteria) this;
        }

        public Criteria andDelGreaterThan(Boolean value) {
            addCriterion("del >", value, "del");
            return (Criteria) this;
        }

        public Criteria andDelGreaterThanOrEqualTo(Boolean value) {
            addCriterion("del >=", value, "del");
            return (Criteria) this;
        }

        public Criteria andDelLessThan(Boolean value) {
            addCriterion("del <", value, "del");
            return (Criteria) this;
        }

        public Criteria andDelLessThanOrEqualTo(Boolean value) {
            addCriterion("del <=", value, "del");
            return (Criteria) this;
        }

        public Criteria andDelIn(List<Boolean> values) {
            addCriterion("del in", values, "del");
            return (Criteria) this;
        }

        public Criteria andDelNotIn(List<Boolean> values) {
            addCriterion("del not in", values, "del");
            return (Criteria) this;
        }

        public Criteria andDelBetween(Boolean value1, Boolean value2) {
            addCriterion("del between", value1, value2, "del");
            return (Criteria) this;
        }

        public Criteria andDelNotBetween(Boolean value1, Boolean value2) {
            addCriterion("del not between", value1, value2, "del");
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

        public Criteria andRelativeImportanceEqualTo(Integer value) {
            addCriterion("relative_importance =", value, "relativeImportance");
            return (Criteria) this;
        }

        public Criteria andRelativeImportanceNotEqualTo(Integer value) {
            addCriterion("relative_importance <>", value, "relativeImportance");
            return (Criteria) this;
        }

        public Criteria andRelativeImportanceGreaterThan(Integer value) {
            addCriterion("relative_importance >", value, "relativeImportance");
            return (Criteria) this;
        }

        public Criteria andRelativeImportanceGreaterThanOrEqualTo(Integer value) {
            addCriterion("relative_importance >=", value, "relativeImportance");
            return (Criteria) this;
        }

        public Criteria andRelativeImportanceLessThan(Integer value) {
            addCriterion("relative_importance <", value, "relativeImportance");
            return (Criteria) this;
        }

        public Criteria andRelativeImportanceLessThanOrEqualTo(Integer value) {
            addCriterion("relative_importance <=", value, "relativeImportance");
            return (Criteria) this;
        }

        public Criteria andRelativeImportanceIn(List<Integer> values) {
            addCriterion("relative_importance in", values, "relativeImportance");
            return (Criteria) this;
        }

        public Criteria andRelativeImportanceNotIn(List<Integer> values) {
            addCriterion("relative_importance not in", values, "relativeImportance");
            return (Criteria) this;
        }

        public Criteria andRelativeImportanceBetween(Integer value1, Integer value2) {
            addCriterion("relative_importance between", value1, value2, "relativeImportance");
            return (Criteria) this;
        }

        public Criteria andRelativeImportanceNotBetween(Integer value1, Integer value2) {
            addCriterion("relative_importance not between", value1, value2, "relativeImportance");
            return (Criteria) this;
        }

        public Criteria andNoticeImportanceIsNull() {
            addCriterion("notice_importance is null");
            return (Criteria) this;
        }

        public Criteria andNoticeImportanceIsNotNull() {
            addCriterion("notice_importance is not null");
            return (Criteria) this;
        }

        public Criteria andNoticeImportanceEqualTo(Integer value) {
            addCriterion("notice_importance =", value, "noticeImportance");
            return (Criteria) this;
        }

        public Criteria andNoticeImportanceNotEqualTo(Integer value) {
            addCriterion("notice_importance <>", value, "noticeImportance");
            return (Criteria) this;
        }

        public Criteria andNoticeImportanceGreaterThan(Integer value) {
            addCriterion("notice_importance >", value, "noticeImportance");
            return (Criteria) this;
        }

        public Criteria andNoticeImportanceGreaterThanOrEqualTo(Integer value) {
            addCriterion("notice_importance >=", value, "noticeImportance");
            return (Criteria) this;
        }

        public Criteria andNoticeImportanceLessThan(Integer value) {
            addCriterion("notice_importance <", value, "noticeImportance");
            return (Criteria) this;
        }

        public Criteria andNoticeImportanceLessThanOrEqualTo(Integer value) {
            addCriterion("notice_importance <=", value, "noticeImportance");
            return (Criteria) this;
        }

        public Criteria andNoticeImportanceIn(List<Integer> values) {
            addCriterion("notice_importance in", values, "noticeImportance");
            return (Criteria) this;
        }

        public Criteria andNoticeImportanceNotIn(List<Integer> values) {
            addCriterion("notice_importance not in", values, "noticeImportance");
            return (Criteria) this;
        }

        public Criteria andNoticeImportanceBetween(Integer value1, Integer value2) {
            addCriterion("notice_importance between", value1, value2, "noticeImportance");
            return (Criteria) this;
        }

        public Criteria andNoticeImportanceNotBetween(Integer value1, Integer value2) {
            addCriterion("notice_importance not between", value1, value2, "noticeImportance");
            return (Criteria) this;
        }

        public Criteria andOrganizationIsNull() {
            addCriterion("organization is null");
            return (Criteria) this;
        }

        public Criteria andOrganizationIsNotNull() {
            addCriterion("organization is not null");
            return (Criteria) this;
        }

        public Criteria andOrganizationEqualTo(Integer value) {
            addCriterion("organization =", value, "organization");
            return (Criteria) this;
        }

        public Criteria andOrganizationNotEqualTo(Integer value) {
            addCriterion("organization <>", value, "organization");
            return (Criteria) this;
        }

        public Criteria andOrganizationGreaterThan(Integer value) {
            addCriterion("organization >", value, "organization");
            return (Criteria) this;
        }

        public Criteria andOrganizationGreaterThanOrEqualTo(Integer value) {
            addCriterion("organization >=", value, "organization");
            return (Criteria) this;
        }

        public Criteria andOrganizationLessThan(Integer value) {
            addCriterion("organization <", value, "organization");
            return (Criteria) this;
        }

        public Criteria andOrganizationLessThanOrEqualTo(Integer value) {
            addCriterion("organization <=", value, "organization");
            return (Criteria) this;
        }

        public Criteria andOrganizationIn(List<Integer> values) {
            addCriterion("organization in", values, "organization");
            return (Criteria) this;
        }

        public Criteria andOrganizationNotIn(List<Integer> values) {
            addCriterion("organization not in", values, "organization");
            return (Criteria) this;
        }

        public Criteria andOrganizationBetween(Integer value1, Integer value2) {
            addCriterion("organization between", value1, value2, "organization");
            return (Criteria) this;
        }

        public Criteria andOrganizationNotBetween(Integer value1, Integer value2) {
            addCriterion("organization not between", value1, value2, "organization");
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