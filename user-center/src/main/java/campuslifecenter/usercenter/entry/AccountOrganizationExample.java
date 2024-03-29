package campuslifecenter.usercenter.entry;

import java.util.ArrayList;
import java.util.List;

public class AccountOrganizationExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AccountOrganizationExample() {
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

        public Criteria andOidIsNull() {
            addCriterion("oid is null");
            return (Criteria) this;
        }

        public Criteria andOidIsNotNull() {
            addCriterion("oid is not null");
            return (Criteria) this;
        }

        public Criteria andOidEqualTo(Integer value) {
            addCriterion("oid =", value, "oid");
            return (Criteria) this;
        }

        public Criteria andOidNotEqualTo(Integer value) {
            addCriterion("oid <>", value, "oid");
            return (Criteria) this;
        }

        public Criteria andOidGreaterThan(Integer value) {
            addCriterion("oid >", value, "oid");
            return (Criteria) this;
        }

        public Criteria andOidGreaterThanOrEqualTo(Integer value) {
            addCriterion("oid >=", value, "oid");
            return (Criteria) this;
        }

        public Criteria andOidLessThan(Integer value) {
            addCriterion("oid <", value, "oid");
            return (Criteria) this;
        }

        public Criteria andOidLessThanOrEqualTo(Integer value) {
            addCriterion("oid <=", value, "oid");
            return (Criteria) this;
        }

        public Criteria andOidIn(List<Integer> values) {
            addCriterion("oid in", values, "oid");
            return (Criteria) this;
        }

        public Criteria andOidNotIn(List<Integer> values) {
            addCriterion("oid not in", values, "oid");
            return (Criteria) this;
        }

        public Criteria andOidBetween(Integer value1, Integer value2) {
            addCriterion("oid between", value1, value2, "oid");
            return (Criteria) this;
        }

        public Criteria andOidNotBetween(Integer value1, Integer value2) {
            addCriterion("oid not between", value1, value2, "oid");
            return (Criteria) this;
        }

        public Criteria andHideIsNull() {
            addCriterion("hide is null");
            return (Criteria) this;
        }

        public Criteria andHideIsNotNull() {
            addCriterion("hide is not null");
            return (Criteria) this;
        }

        public Criteria andHideEqualTo(Boolean value) {
            addCriterion("hide =", value, "hide");
            return (Criteria) this;
        }

        public Criteria andHideNotEqualTo(Boolean value) {
            addCriterion("hide <>", value, "hide");
            return (Criteria) this;
        }

        public Criteria andHideGreaterThan(Boolean value) {
            addCriterion("hide >", value, "hide");
            return (Criteria) this;
        }

        public Criteria andHideGreaterThanOrEqualTo(Boolean value) {
            addCriterion("hide >=", value, "hide");
            return (Criteria) this;
        }

        public Criteria andHideLessThan(Boolean value) {
            addCriterion("hide <", value, "hide");
            return (Criteria) this;
        }

        public Criteria andHideLessThanOrEqualTo(Boolean value) {
            addCriterion("hide <=", value, "hide");
            return (Criteria) this;
        }

        public Criteria andHideIn(List<Boolean> values) {
            addCriterion("hide in", values, "hide");
            return (Criteria) this;
        }

        public Criteria andHideNotIn(List<Boolean> values) {
            addCriterion("hide not in", values, "hide");
            return (Criteria) this;
        }

        public Criteria andHideBetween(Boolean value1, Boolean value2) {
            addCriterion("hide between", value1, value2, "hide");
            return (Criteria) this;
        }

        public Criteria andHideNotBetween(Boolean value1, Boolean value2) {
            addCriterion("hide not between", value1, value2, "hide");
            return (Criteria) this;
        }

        public Criteria andAccountAcceptIsNull() {
            addCriterion("account_accept is null");
            return (Criteria) this;
        }

        public Criteria andAccountAcceptIsNotNull() {
            addCriterion("account_accept is not null");
            return (Criteria) this;
        }

        public Criteria andAccountAcceptEqualTo(Boolean value) {
            addCriterion("account_accept =", value, "accountAccept");
            return (Criteria) this;
        }

        public Criteria andAccountAcceptNotEqualTo(Boolean value) {
            addCriterion("account_accept <>", value, "accountAccept");
            return (Criteria) this;
        }

        public Criteria andAccountAcceptGreaterThan(Boolean value) {
            addCriterion("account_accept >", value, "accountAccept");
            return (Criteria) this;
        }

        public Criteria andAccountAcceptGreaterThanOrEqualTo(Boolean value) {
            addCriterion("account_accept >=", value, "accountAccept");
            return (Criteria) this;
        }

        public Criteria andAccountAcceptLessThan(Boolean value) {
            addCriterion("account_accept <", value, "accountAccept");
            return (Criteria) this;
        }

        public Criteria andAccountAcceptLessThanOrEqualTo(Boolean value) {
            addCriterion("account_accept <=", value, "accountAccept");
            return (Criteria) this;
        }

        public Criteria andAccountAcceptIn(List<Boolean> values) {
            addCriterion("account_accept in", values, "accountAccept");
            return (Criteria) this;
        }

        public Criteria andAccountAcceptNotIn(List<Boolean> values) {
            addCriterion("account_accept not in", values, "accountAccept");
            return (Criteria) this;
        }

        public Criteria andAccountAcceptBetween(Boolean value1, Boolean value2) {
            addCriterion("account_accept between", value1, value2, "accountAccept");
            return (Criteria) this;
        }

        public Criteria andAccountAcceptNotBetween(Boolean value1, Boolean value2) {
            addCriterion("account_accept not between", value1, value2, "accountAccept");
            return (Criteria) this;
        }

        public Criteria andOrganizationAcceptIsNull() {
            addCriterion("organization_accept is null");
            return (Criteria) this;
        }

        public Criteria andOrganizationAcceptIsNotNull() {
            addCriterion("organization_accept is not null");
            return (Criteria) this;
        }

        public Criteria andOrganizationAcceptEqualTo(Boolean value) {
            addCriterion("organization_accept =", value, "organizationAccept");
            return (Criteria) this;
        }

        public Criteria andOrganizationAcceptNotEqualTo(Boolean value) {
            addCriterion("organization_accept <>", value, "organizationAccept");
            return (Criteria) this;
        }

        public Criteria andOrganizationAcceptGreaterThan(Boolean value) {
            addCriterion("organization_accept >", value, "organizationAccept");
            return (Criteria) this;
        }

        public Criteria andOrganizationAcceptGreaterThanOrEqualTo(Boolean value) {
            addCriterion("organization_accept >=", value, "organizationAccept");
            return (Criteria) this;
        }

        public Criteria andOrganizationAcceptLessThan(Boolean value) {
            addCriterion("organization_accept <", value, "organizationAccept");
            return (Criteria) this;
        }

        public Criteria andOrganizationAcceptLessThanOrEqualTo(Boolean value) {
            addCriterion("organization_accept <=", value, "organizationAccept");
            return (Criteria) this;
        }

        public Criteria andOrganizationAcceptIn(List<Boolean> values) {
            addCriterion("organization_accept in", values, "organizationAccept");
            return (Criteria) this;
        }

        public Criteria andOrganizationAcceptNotIn(List<Boolean> values) {
            addCriterion("organization_accept not in", values, "organizationAccept");
            return (Criteria) this;
        }

        public Criteria andOrganizationAcceptBetween(Boolean value1, Boolean value2) {
            addCriterion("organization_accept between", value1, value2, "organizationAccept");
            return (Criteria) this;
        }

        public Criteria andOrganizationAcceptNotBetween(Boolean value1, Boolean value2) {
            addCriterion("organization_accept not between", value1, value2, "organizationAccept");
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