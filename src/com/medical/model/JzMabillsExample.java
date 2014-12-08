package com.medical.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JzMabillsExample {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table YLJZN.JZ_MABILLS
     *
     * @ibatorgenerated Mon Dec 08 12:18:36 CST 2014
     */
    protected String orderByClause;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table YLJZN.JZ_MABILLS
     *
     * @ibatorgenerated Mon Dec 08 12:18:36 CST 2014
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table YLJZN.JZ_MABILLS
     *
     * @ibatorgenerated Mon Dec 08 12:18:36 CST 2014
     */
    public JzMabillsExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table YLJZN.JZ_MABILLS
     *
     * @ibatorgenerated Mon Dec 08 12:18:36 CST 2014
     */
    protected JzMabillsExample(JzMabillsExample example) {
        this.orderByClause = example.orderByClause;
        this.oredCriteria = example.oredCriteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table YLJZN.JZ_MABILLS
     *
     * @ibatorgenerated Mon Dec 08 12:18:36 CST 2014
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table YLJZN.JZ_MABILLS
     *
     * @ibatorgenerated Mon Dec 08 12:18:36 CST 2014
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table YLJZN.JZ_MABILLS
     *
     * @ibatorgenerated Mon Dec 08 12:18:36 CST 2014
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table YLJZN.JZ_MABILLS
     *
     * @ibatorgenerated Mon Dec 08 12:18:36 CST 2014
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table YLJZN.JZ_MABILLS
     *
     * @ibatorgenerated Mon Dec 08 12:18:36 CST 2014
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table YLJZN.JZ_MABILLS
     *
     * @ibatorgenerated Mon Dec 08 12:18:36 CST 2014
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table YLJZN.JZ_MABILLS
     *
     * @ibatorgenerated Mon Dec 08 12:18:36 CST 2014
     */
    public void clear() {
        oredCriteria.clear();
    }

    /**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table YLJZN.JZ_MABILLS
     *
     * @ibatorgenerated Mon Dec 08 12:18:36 CST 2014
     */
    public static class Criteria {
        protected List<String> criteriaWithoutValue;

        protected List<Map<String, Object>> criteriaWithSingleValue;

        protected List<Map<String, Object>> criteriaWithListValue;

        protected List<Map<String, Object>> criteriaWithBetweenValue;

        protected Criteria() {
            super();
            criteriaWithoutValue = new ArrayList<String>();
            criteriaWithSingleValue = new ArrayList<Map<String, Object>>();
            criteriaWithListValue = new ArrayList<Map<String, Object>>();
            criteriaWithBetweenValue = new ArrayList<Map<String, Object>>();
        }

        public boolean isValid() {
            return criteriaWithoutValue.size() > 0
                || criteriaWithSingleValue.size() > 0
                || criteriaWithListValue.size() > 0
                || criteriaWithBetweenValue.size() > 0;
        }

        public List<String> getCriteriaWithoutValue() {
            return criteriaWithoutValue;
        }

        public List<Map<String, Object>> getCriteriaWithSingleValue() {
            return criteriaWithSingleValue;
        }

        public List<Map<String, Object>> getCriteriaWithListValue() {
            return criteriaWithListValue;
        }

        public List<Map<String, Object>> getCriteriaWithBetweenValue() {
            return criteriaWithBetweenValue;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteriaWithoutValue.add(condition);
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("condition", condition);
            map.put("value", value);
            criteriaWithSingleValue.add(map);
        }

        protected void addCriterion(String condition, List<? extends Object> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("condition", condition);
            map.put("values", values);
            criteriaWithListValue.add(map);
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            List<Object> list = new ArrayList<Object>();
            list.add(value1);
            list.add(value2);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("condition", condition);
            map.put("values", list);
            criteriaWithBetweenValue.add(map);
        }

        public Criteria andBillIdIsNull() {
            addCriterion("BILL_ID is null");
            return this;
        }

        public Criteria andBillIdIsNotNull() {
            addCriterion("BILL_ID is not null");
            return this;
        }

        public Criteria andBillIdEqualTo(Long value) {
            addCriterion("BILL_ID =", value, "billId");
            return this;
        }

        public Criteria andBillIdNotEqualTo(Long value) {
            addCriterion("BILL_ID <>", value, "billId");
            return this;
        }

        public Criteria andBillIdGreaterThan(Long value) {
            addCriterion("BILL_ID >", value, "billId");
            return this;
        }

        public Criteria andBillIdGreaterThanOrEqualTo(Long value) {
            addCriterion("BILL_ID >=", value, "billId");
            return this;
        }

        public Criteria andBillIdLessThan(Long value) {
            addCriterion("BILL_ID <", value, "billId");
            return this;
        }

        public Criteria andBillIdLessThanOrEqualTo(Long value) {
            addCriterion("BILL_ID <=", value, "billId");
            return this;
        }

        public Criteria andBillIdIn(List<Long> values) {
            addCriterion("BILL_ID in", values, "billId");
            return this;
        }

        public Criteria andBillIdNotIn(List<Long> values) {
            addCriterion("BILL_ID not in", values, "billId");
            return this;
        }

        public Criteria andBillIdBetween(Long value1, Long value2) {
            addCriterion("BILL_ID between", value1, value2, "billId");
            return this;
        }

        public Criteria andBillIdNotBetween(Long value1, Long value2) {
            addCriterion("BILL_ID not between", value1, value2, "billId");
            return this;
        }

        public Criteria andMaIdIsNull() {
            addCriterion("MA_ID is null");
            return this;
        }

        public Criteria andMaIdIsNotNull() {
            addCriterion("MA_ID is not null");
            return this;
        }

        public Criteria andMaIdEqualTo(Long value) {
            addCriterion("MA_ID =", value, "maId");
            return this;
        }

        public Criteria andMaIdNotEqualTo(Long value) {
            addCriterion("MA_ID <>", value, "maId");
            return this;
        }

        public Criteria andMaIdGreaterThan(Long value) {
            addCriterion("MA_ID >", value, "maId");
            return this;
        }

        public Criteria andMaIdGreaterThanOrEqualTo(Long value) {
            addCriterion("MA_ID >=", value, "maId");
            return this;
        }

        public Criteria andMaIdLessThan(Long value) {
            addCriterion("MA_ID <", value, "maId");
            return this;
        }

        public Criteria andMaIdLessThanOrEqualTo(Long value) {
            addCriterion("MA_ID <=", value, "maId");
            return this;
        }

        public Criteria andMaIdIn(List<Long> values) {
            addCriterion("MA_ID in", values, "maId");
            return this;
        }

        public Criteria andMaIdNotIn(List<Long> values) {
            addCriterion("MA_ID not in", values, "maId");
            return this;
        }

        public Criteria andMaIdBetween(Long value1, Long value2) {
            addCriterion("MA_ID between", value1, value2, "maId");
            return this;
        }

        public Criteria andMaIdNotBetween(Long value1, Long value2) {
            addCriterion("MA_ID not between", value1, value2, "maId");
            return this;
        }

        public Criteria andFamilynoIsNull() {
            addCriterion("FAMILYNO is null");
            return this;
        }

        public Criteria andFamilynoIsNotNull() {
            addCriterion("FAMILYNO is not null");
            return this;
        }

        public Criteria andFamilynoEqualTo(String value) {
            addCriterion("FAMILYNO =", value, "familyno");
            return this;
        }

        public Criteria andFamilynoNotEqualTo(String value) {
            addCriterion("FAMILYNO <>", value, "familyno");
            return this;
        }

        public Criteria andFamilynoGreaterThan(String value) {
            addCriterion("FAMILYNO >", value, "familyno");
            return this;
        }

        public Criteria andFamilynoGreaterThanOrEqualTo(String value) {
            addCriterion("FAMILYNO >=", value, "familyno");
            return this;
        }

        public Criteria andFamilynoLessThan(String value) {
            addCriterion("FAMILYNO <", value, "familyno");
            return this;
        }

        public Criteria andFamilynoLessThanOrEqualTo(String value) {
            addCriterion("FAMILYNO <=", value, "familyno");
            return this;
        }

        public Criteria andFamilynoLike(String value) {
            addCriterion("FAMILYNO like", value, "familyno");
            return this;
        }

        public Criteria andFamilynoNotLike(String value) {
            addCriterion("FAMILYNO not like", value, "familyno");
            return this;
        }

        public Criteria andFamilynoIn(List<String> values) {
            addCriterion("FAMILYNO in", values, "familyno");
            return this;
        }

        public Criteria andFamilynoNotIn(List<String> values) {
            addCriterion("FAMILYNO not in", values, "familyno");
            return this;
        }

        public Criteria andFamilynoBetween(String value1, String value2) {
            addCriterion("FAMILYNO between", value1, value2, "familyno");
            return this;
        }

        public Criteria andFamilynoNotBetween(String value1, String value2) {
            addCriterion("FAMILYNO not between", value1, value2, "familyno");
            return this;
        }

        public Criteria andMembernameIsNull() {
            addCriterion("MEMBERNAME is null");
            return this;
        }

        public Criteria andMembernameIsNotNull() {
            addCriterion("MEMBERNAME is not null");
            return this;
        }

        public Criteria andMembernameEqualTo(String value) {
            addCriterion("MEMBERNAME =", value, "membername");
            return this;
        }

        public Criteria andMembernameNotEqualTo(String value) {
            addCriterion("MEMBERNAME <>", value, "membername");
            return this;
        }

        public Criteria andMembernameGreaterThan(String value) {
            addCriterion("MEMBERNAME >", value, "membername");
            return this;
        }

        public Criteria andMembernameGreaterThanOrEqualTo(String value) {
            addCriterion("MEMBERNAME >=", value, "membername");
            return this;
        }

        public Criteria andMembernameLessThan(String value) {
            addCriterion("MEMBERNAME <", value, "membername");
            return this;
        }

        public Criteria andMembernameLessThanOrEqualTo(String value) {
            addCriterion("MEMBERNAME <=", value, "membername");
            return this;
        }

        public Criteria andMembernameLike(String value) {
            addCriterion("MEMBERNAME like", value, "membername");
            return this;
        }

        public Criteria andMembernameNotLike(String value) {
            addCriterion("MEMBERNAME not like", value, "membername");
            return this;
        }

        public Criteria andMembernameIn(List<String> values) {
            addCriterion("MEMBERNAME in", values, "membername");
            return this;
        }

        public Criteria andMembernameNotIn(List<String> values) {
            addCriterion("MEMBERNAME not in", values, "membername");
            return this;
        }

        public Criteria andMembernameBetween(String value1, String value2) {
            addCriterion("MEMBERNAME between", value1, value2, "membername");
            return this;
        }

        public Criteria andMembernameNotBetween(String value1, String value2) {
            addCriterion("MEMBERNAME not between", value1, value2, "membername");
            return this;
        }

        public Criteria andPaperidIsNull() {
            addCriterion("PAPERID is null");
            return this;
        }

        public Criteria andPaperidIsNotNull() {
            addCriterion("PAPERID is not null");
            return this;
        }

        public Criteria andPaperidEqualTo(String value) {
            addCriterion("PAPERID =", value, "paperid");
            return this;
        }

        public Criteria andPaperidNotEqualTo(String value) {
            addCriterion("PAPERID <>", value, "paperid");
            return this;
        }

        public Criteria andPaperidGreaterThan(String value) {
            addCriterion("PAPERID >", value, "paperid");
            return this;
        }

        public Criteria andPaperidGreaterThanOrEqualTo(String value) {
            addCriterion("PAPERID >=", value, "paperid");
            return this;
        }

        public Criteria andPaperidLessThan(String value) {
            addCriterion("PAPERID <", value, "paperid");
            return this;
        }

        public Criteria andPaperidLessThanOrEqualTo(String value) {
            addCriterion("PAPERID <=", value, "paperid");
            return this;
        }

        public Criteria andPaperidLike(String value) {
            addCriterion("PAPERID like", value, "paperid");
            return this;
        }

        public Criteria andPaperidNotLike(String value) {
            addCriterion("PAPERID not like", value, "paperid");
            return this;
        }

        public Criteria andPaperidIn(List<String> values) {
            addCriterion("PAPERID in", values, "paperid");
            return this;
        }

        public Criteria andPaperidNotIn(List<String> values) {
            addCriterion("PAPERID not in", values, "paperid");
            return this;
        }

        public Criteria andPaperidBetween(String value1, String value2) {
            addCriterion("PAPERID between", value1, value2, "paperid");
            return this;
        }

        public Criteria andPaperidNotBetween(String value1, String value2) {
            addCriterion("PAPERID not between", value1, value2, "paperid");
            return this;
        }

        public Criteria andCtimeIsNull() {
            addCriterion("CTIME is null");
            return this;
        }

        public Criteria andCtimeIsNotNull() {
            addCriterion("CTIME is not null");
            return this;
        }

        public Criteria andCtimeEqualTo(Date value) {
            addCriterion("CTIME =", value, "ctime");
            return this;
        }

        public Criteria andCtimeNotEqualTo(Date value) {
            addCriterion("CTIME <>", value, "ctime");
            return this;
        }

        public Criteria andCtimeGreaterThan(Date value) {
            addCriterion("CTIME >", value, "ctime");
            return this;
        }

        public Criteria andCtimeGreaterThanOrEqualTo(Date value) {
            addCriterion("CTIME >=", value, "ctime");
            return this;
        }

        public Criteria andCtimeLessThan(Date value) {
            addCriterion("CTIME <", value, "ctime");
            return this;
        }

        public Criteria andCtimeLessThanOrEqualTo(Date value) {
            addCriterion("CTIME <=", value, "ctime");
            return this;
        }

        public Criteria andCtimeIn(List<Date> values) {
            addCriterion("CTIME in", values, "ctime");
            return this;
        }

        public Criteria andCtimeNotIn(List<Date> values) {
            addCriterion("CTIME not in", values, "ctime");
            return this;
        }

        public Criteria andCtimeBetween(Date value1, Date value2) {
            addCriterion("CTIME between", value1, value2, "ctime");
            return this;
        }

        public Criteria andCtimeNotBetween(Date value1, Date value2) {
            addCriterion("CTIME not between", value1, value2, "ctime");
            return this;
        }

        public Criteria andUtimeIsNull() {
            addCriterion("UTIME is null");
            return this;
        }

        public Criteria andUtimeIsNotNull() {
            addCriterion("UTIME is not null");
            return this;
        }

        public Criteria andUtimeEqualTo(Date value) {
            addCriterion("UTIME =", value, "utime");
            return this;
        }

        public Criteria andUtimeNotEqualTo(Date value) {
            addCriterion("UTIME <>", value, "utime");
            return this;
        }

        public Criteria andUtimeGreaterThan(Date value) {
            addCriterion("UTIME >", value, "utime");
            return this;
        }

        public Criteria andUtimeGreaterThanOrEqualTo(Date value) {
            addCriterion("UTIME >=", value, "utime");
            return this;
        }

        public Criteria andUtimeLessThan(Date value) {
            addCriterion("UTIME <", value, "utime");
            return this;
        }

        public Criteria andUtimeLessThanOrEqualTo(Date value) {
            addCriterion("UTIME <=", value, "utime");
            return this;
        }

        public Criteria andUtimeIn(List<Date> values) {
            addCriterion("UTIME in", values, "utime");
            return this;
        }

        public Criteria andUtimeNotIn(List<Date> values) {
            addCriterion("UTIME not in", values, "utime");
            return this;
        }

        public Criteria andUtimeBetween(Date value1, Date value2) {
            addCriterion("UTIME between", value1, value2, "utime");
            return this;
        }

        public Criteria andUtimeNotBetween(Date value1, Date value2) {
            addCriterion("UTIME not between", value1, value2, "utime");
            return this;
        }

        public Criteria andAssispayIsNull() {
            addCriterion("ASSISPAY is null");
            return this;
        }

        public Criteria andAssispayIsNotNull() {
            addCriterion("ASSISPAY is not null");
            return this;
        }

        public Criteria andAssispayEqualTo(BigDecimal value) {
            addCriterion("ASSISPAY =", value, "assispay");
            return this;
        }

        public Criteria andAssispayNotEqualTo(BigDecimal value) {
            addCriterion("ASSISPAY <>", value, "assispay");
            return this;
        }

        public Criteria andAssispayGreaterThan(BigDecimal value) {
            addCriterion("ASSISPAY >", value, "assispay");
            return this;
        }

        public Criteria andAssispayGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ASSISPAY >=", value, "assispay");
            return this;
        }

        public Criteria andAssispayLessThan(BigDecimal value) {
            addCriterion("ASSISPAY <", value, "assispay");
            return this;
        }

        public Criteria andAssispayLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ASSISPAY <=", value, "assispay");
            return this;
        }

        public Criteria andAssispayIn(List<BigDecimal> values) {
            addCriterion("ASSISPAY in", values, "assispay");
            return this;
        }

        public Criteria andAssispayNotIn(List<BigDecimal> values) {
            addCriterion("ASSISPAY not in", values, "assispay");
            return this;
        }

        public Criteria andAssispayBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ASSISPAY between", value1, value2, "assispay");
            return this;
        }

        public Criteria andAssispayNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ASSISPAY not between", value1, value2, "assispay");
            return this;
        }

        public Criteria andDsIsNull() {
            addCriterion("DS is null");
            return this;
        }

        public Criteria andDsIsNotNull() {
            addCriterion("DS is not null");
            return this;
        }

        public Criteria andDsEqualTo(String value) {
            addCriterion("DS =", value, "ds");
            return this;
        }

        public Criteria andDsNotEqualTo(String value) {
            addCriterion("DS <>", value, "ds");
            return this;
        }

        public Criteria andDsGreaterThan(String value) {
            addCriterion("DS >", value, "ds");
            return this;
        }

        public Criteria andDsGreaterThanOrEqualTo(String value) {
            addCriterion("DS >=", value, "ds");
            return this;
        }

        public Criteria andDsLessThan(String value) {
            addCriterion("DS <", value, "ds");
            return this;
        }

        public Criteria andDsLessThanOrEqualTo(String value) {
            addCriterion("DS <=", value, "ds");
            return this;
        }

        public Criteria andDsLike(String value) {
            addCriterion("DS like", value, "ds");
            return this;
        }

        public Criteria andDsNotLike(String value) {
            addCriterion("DS not like", value, "ds");
            return this;
        }

        public Criteria andDsIn(List<String> values) {
            addCriterion("DS in", values, "ds");
            return this;
        }

        public Criteria andDsNotIn(List<String> values) {
            addCriterion("DS not in", values, "ds");
            return this;
        }

        public Criteria andDsBetween(String value1, String value2) {
            addCriterion("DS between", value1, value2, "ds");
            return this;
        }

        public Criteria andDsNotBetween(String value1, String value2) {
            addCriterion("DS not between", value1, value2, "ds");
            return this;
        }

        public Criteria andSbIdIsNull() {
            addCriterion("SB_ID is null");
            return this;
        }

        public Criteria andSbIdIsNotNull() {
            addCriterion("SB_ID is not null");
            return this;
        }

        public Criteria andSbIdEqualTo(String value) {
            addCriterion("SB_ID =", value, "sbId");
            return this;
        }

        public Criteria andSbIdNotEqualTo(String value) {
            addCriterion("SB_ID <>", value, "sbId");
            return this;
        }

        public Criteria andSbIdGreaterThan(String value) {
            addCriterion("SB_ID >", value, "sbId");
            return this;
        }

        public Criteria andSbIdGreaterThanOrEqualTo(String value) {
            addCriterion("SB_ID >=", value, "sbId");
            return this;
        }

        public Criteria andSbIdLessThan(String value) {
            addCriterion("SB_ID <", value, "sbId");
            return this;
        }

        public Criteria andSbIdLessThanOrEqualTo(String value) {
            addCriterion("SB_ID <=", value, "sbId");
            return this;
        }

        public Criteria andSbIdLike(String value) {
            addCriterion("SB_ID like", value, "sbId");
            return this;
        }

        public Criteria andSbIdNotLike(String value) {
            addCriterion("SB_ID not like", value, "sbId");
            return this;
        }

        public Criteria andSbIdIn(List<String> values) {
            addCriterion("SB_ID in", values, "sbId");
            return this;
        }

        public Criteria andSbIdNotIn(List<String> values) {
            addCriterion("SB_ID not in", values, "sbId");
            return this;
        }

        public Criteria andSbIdBetween(String value1, String value2) {
            addCriterion("SB_ID between", value1, value2, "sbId");
            return this;
        }

        public Criteria andSbIdNotBetween(String value1, String value2) {
            addCriterion("SB_ID not between", value1, value2, "sbId");
            return this;
        }

        public Criteria andBatchnameIsNull() {
            addCriterion("BATCHNAME is null");
            return this;
        }

        public Criteria andBatchnameIsNotNull() {
            addCriterion("BATCHNAME is not null");
            return this;
        }

        public Criteria andBatchnameEqualTo(String value) {
            addCriterion("BATCHNAME =", value, "batchname");
            return this;
        }

        public Criteria andBatchnameNotEqualTo(String value) {
            addCriterion("BATCHNAME <>", value, "batchname");
            return this;
        }

        public Criteria andBatchnameGreaterThan(String value) {
            addCriterion("BATCHNAME >", value, "batchname");
            return this;
        }

        public Criteria andBatchnameGreaterThanOrEqualTo(String value) {
            addCriterion("BATCHNAME >=", value, "batchname");
            return this;
        }

        public Criteria andBatchnameLessThan(String value) {
            addCriterion("BATCHNAME <", value, "batchname");
            return this;
        }

        public Criteria andBatchnameLessThanOrEqualTo(String value) {
            addCriterion("BATCHNAME <=", value, "batchname");
            return this;
        }

        public Criteria andBatchnameLike(String value) {
            addCriterion("BATCHNAME like", value, "batchname");
            return this;
        }

        public Criteria andBatchnameNotLike(String value) {
            addCriterion("BATCHNAME not like", value, "batchname");
            return this;
        }

        public Criteria andBatchnameIn(List<String> values) {
            addCriterion("BATCHNAME in", values, "batchname");
            return this;
        }

        public Criteria andBatchnameNotIn(List<String> values) {
            addCriterion("BATCHNAME not in", values, "batchname");
            return this;
        }

        public Criteria andBatchnameBetween(String value1, String value2) {
            addCriterion("BATCHNAME between", value1, value2, "batchname");
            return this;
        }

        public Criteria andBatchnameNotBetween(String value1, String value2) {
            addCriterion("BATCHNAME not between", value1, value2, "batchname");
            return this;
        }

        public Criteria andMasternameIsNull() {
            addCriterion("MASTERNAME is null");
            return this;
        }

        public Criteria andMasternameIsNotNull() {
            addCriterion("MASTERNAME is not null");
            return this;
        }

        public Criteria andMasternameEqualTo(String value) {
            addCriterion("MASTERNAME =", value, "mastername");
            return this;
        }

        public Criteria andMasternameNotEqualTo(String value) {
            addCriterion("MASTERNAME <>", value, "mastername");
            return this;
        }

        public Criteria andMasternameGreaterThan(String value) {
            addCriterion("MASTERNAME >", value, "mastername");
            return this;
        }

        public Criteria andMasternameGreaterThanOrEqualTo(String value) {
            addCriterion("MASTERNAME >=", value, "mastername");
            return this;
        }

        public Criteria andMasternameLessThan(String value) {
            addCriterion("MASTERNAME <", value, "mastername");
            return this;
        }

        public Criteria andMasternameLessThanOrEqualTo(String value) {
            addCriterion("MASTERNAME <=", value, "mastername");
            return this;
        }

        public Criteria andMasternameLike(String value) {
            addCriterion("MASTERNAME like", value, "mastername");
            return this;
        }

        public Criteria andMasternameNotLike(String value) {
            addCriterion("MASTERNAME not like", value, "mastername");
            return this;
        }

        public Criteria andMasternameIn(List<String> values) {
            addCriterion("MASTERNAME in", values, "mastername");
            return this;
        }

        public Criteria andMasternameNotIn(List<String> values) {
            addCriterion("MASTERNAME not in", values, "mastername");
            return this;
        }

        public Criteria andMasternameBetween(String value1, String value2) {
            addCriterion("MASTERNAME between", value1, value2, "mastername");
            return this;
        }

        public Criteria andMasternameNotBetween(String value1, String value2) {
            addCriterion("MASTERNAME not between", value1, value2, "mastername");
            return this;
        }

        public Criteria andMasteridcardIsNull() {
            addCriterion("MASTERIDCARD is null");
            return this;
        }

        public Criteria andMasteridcardIsNotNull() {
            addCriterion("MASTERIDCARD is not null");
            return this;
        }

        public Criteria andMasteridcardEqualTo(String value) {
            addCriterion("MASTERIDCARD =", value, "masteridcard");
            return this;
        }

        public Criteria andMasteridcardNotEqualTo(String value) {
            addCriterion("MASTERIDCARD <>", value, "masteridcard");
            return this;
        }

        public Criteria andMasteridcardGreaterThan(String value) {
            addCriterion("MASTERIDCARD >", value, "masteridcard");
            return this;
        }

        public Criteria andMasteridcardGreaterThanOrEqualTo(String value) {
            addCriterion("MASTERIDCARD >=", value, "masteridcard");
            return this;
        }

        public Criteria andMasteridcardLessThan(String value) {
            addCriterion("MASTERIDCARD <", value, "masteridcard");
            return this;
        }

        public Criteria andMasteridcardLessThanOrEqualTo(String value) {
            addCriterion("MASTERIDCARD <=", value, "masteridcard");
            return this;
        }

        public Criteria andMasteridcardLike(String value) {
            addCriterion("MASTERIDCARD like", value, "masteridcard");
            return this;
        }

        public Criteria andMasteridcardNotLike(String value) {
            addCriterion("MASTERIDCARD not like", value, "masteridcard");
            return this;
        }

        public Criteria andMasteridcardIn(List<String> values) {
            addCriterion("MASTERIDCARD in", values, "masteridcard");
            return this;
        }

        public Criteria andMasteridcardNotIn(List<String> values) {
            addCriterion("MASTERIDCARD not in", values, "masteridcard");
            return this;
        }

        public Criteria andMasteridcardBetween(String value1, String value2) {
            addCriterion("MASTERIDCARD between", value1, value2, "masteridcard");
            return this;
        }

        public Criteria andMasteridcardNotBetween(String value1, String value2) {
            addCriterion("MASTERIDCARD not between", value1, value2, "masteridcard");
            return this;
        }

        public Criteria andBankAccountIsNull() {
            addCriterion("BANK_ACCOUNT is null");
            return this;
        }

        public Criteria andBankAccountIsNotNull() {
            addCriterion("BANK_ACCOUNT is not null");
            return this;
        }

        public Criteria andBankAccountEqualTo(String value) {
            addCriterion("BANK_ACCOUNT =", value, "bankAccount");
            return this;
        }

        public Criteria andBankAccountNotEqualTo(String value) {
            addCriterion("BANK_ACCOUNT <>", value, "bankAccount");
            return this;
        }

        public Criteria andBankAccountGreaterThan(String value) {
            addCriterion("BANK_ACCOUNT >", value, "bankAccount");
            return this;
        }

        public Criteria andBankAccountGreaterThanOrEqualTo(String value) {
            addCriterion("BANK_ACCOUNT >=", value, "bankAccount");
            return this;
        }

        public Criteria andBankAccountLessThan(String value) {
            addCriterion("BANK_ACCOUNT <", value, "bankAccount");
            return this;
        }

        public Criteria andBankAccountLessThanOrEqualTo(String value) {
            addCriterion("BANK_ACCOUNT <=", value, "bankAccount");
            return this;
        }

        public Criteria andBankAccountLike(String value) {
            addCriterion("BANK_ACCOUNT like", value, "bankAccount");
            return this;
        }

        public Criteria andBankAccountNotLike(String value) {
            addCriterion("BANK_ACCOUNT not like", value, "bankAccount");
            return this;
        }

        public Criteria andBankAccountIn(List<String> values) {
            addCriterion("BANK_ACCOUNT in", values, "bankAccount");
            return this;
        }

        public Criteria andBankAccountNotIn(List<String> values) {
            addCriterion("BANK_ACCOUNT not in", values, "bankAccount");
            return this;
        }

        public Criteria andBankAccountBetween(String value1, String value2) {
            addCriterion("BANK_ACCOUNT between", value1, value2, "bankAccount");
            return this;
        }

        public Criteria andBankAccountNotBetween(String value1, String value2) {
            addCriterion("BANK_ACCOUNT not between", value1, value2, "bankAccount");
            return this;
        }
    }
}