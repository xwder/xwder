package com.xwder.framework.domain.cqust;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class KyStudentExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public KyStudentExample() {
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

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andStudentNoIsNull() {
            addCriterion("student_no is null");
            return (Criteria) this;
        }

        public Criteria andStudentNoIsNotNull() {
            addCriterion("student_no is not null");
            return (Criteria) this;
        }

        public Criteria andStudentNoEqualTo(String value) {
            addCriterion("student_no =", value, "studentNo");
            return (Criteria) this;
        }

        public Criteria andStudentNoNotEqualTo(String value) {
            addCriterion("student_no <>", value, "studentNo");
            return (Criteria) this;
        }

        public Criteria andStudentNoGreaterThan(String value) {
            addCriterion("student_no >", value, "studentNo");
            return (Criteria) this;
        }

        public Criteria andStudentNoGreaterThanOrEqualTo(String value) {
            addCriterion("student_no >=", value, "studentNo");
            return (Criteria) this;
        }

        public Criteria andStudentNoLessThan(String value) {
            addCriterion("student_no <", value, "studentNo");
            return (Criteria) this;
        }

        public Criteria andStudentNoLessThanOrEqualTo(String value) {
            addCriterion("student_no <=", value, "studentNo");
            return (Criteria) this;
        }

        public Criteria andStudentNoLike(String value) {
            addCriterion("student_no like", value, "studentNo");
            return (Criteria) this;
        }

        public Criteria andStudentNoNotLike(String value) {
            addCriterion("student_no not like", value, "studentNo");
            return (Criteria) this;
        }

        public Criteria andStudentNoIn(List<String> values) {
            addCriterion("student_no in", values, "studentNo");
            return (Criteria) this;
        }

        public Criteria andStudentNoNotIn(List<String> values) {
            addCriterion("student_no not in", values, "studentNo");
            return (Criteria) this;
        }

        public Criteria andStudentNoBetween(String value1, String value2) {
            addCriterion("student_no between", value1, value2, "studentNo");
            return (Criteria) this;
        }

        public Criteria andStudentNoNotBetween(String value1, String value2) {
            addCriterion("student_no not between", value1, value2, "studentNo");
            return (Criteria) this;
        }

        public Criteria andStudentNameIsNull() {
            addCriterion("student_name is null");
            return (Criteria) this;
        }

        public Criteria andStudentNameIsNotNull() {
            addCriterion("student_name is not null");
            return (Criteria) this;
        }

        public Criteria andStudentNameEqualTo(String value) {
            addCriterion("student_name =", value, "studentName");
            return (Criteria) this;
        }

        public Criteria andStudentNameNotEqualTo(String value) {
            addCriterion("student_name <>", value, "studentName");
            return (Criteria) this;
        }

        public Criteria andStudentNameGreaterThan(String value) {
            addCriterion("student_name >", value, "studentName");
            return (Criteria) this;
        }

        public Criteria andStudentNameGreaterThanOrEqualTo(String value) {
            addCriterion("student_name >=", value, "studentName");
            return (Criteria) this;
        }

        public Criteria andStudentNameLessThan(String value) {
            addCriterion("student_name <", value, "studentName");
            return (Criteria) this;
        }

        public Criteria andStudentNameLessThanOrEqualTo(String value) {
            addCriterion("student_name <=", value, "studentName");
            return (Criteria) this;
        }

        public Criteria andStudentNameLike(String value) {
            addCriterion("student_name like", value, "studentName");
            return (Criteria) this;
        }

        public Criteria andStudentNameNotLike(String value) {
            addCriterion("student_name not like", value, "studentName");
            return (Criteria) this;
        }

        public Criteria andStudentNameIn(List<String> values) {
            addCriterion("student_name in", values, "studentName");
            return (Criteria) this;
        }

        public Criteria andStudentNameNotIn(List<String> values) {
            addCriterion("student_name not in", values, "studentName");
            return (Criteria) this;
        }

        public Criteria andStudentNameBetween(String value1, String value2) {
            addCriterion("student_name between", value1, value2, "studentName");
            return (Criteria) this;
        }

        public Criteria andStudentNameNotBetween(String value1, String value2) {
            addCriterion("student_name not between", value1, value2, "studentName");
            return (Criteria) this;
        }

        public Criteria andNationIdIsNull() {
            addCriterion("nation_id is null");
            return (Criteria) this;
        }

        public Criteria andNationIdIsNotNull() {
            addCriterion("nation_id is not null");
            return (Criteria) this;
        }

        public Criteria andNationIdEqualTo(String value) {
            addCriterion("nation_id =", value, "nationId");
            return (Criteria) this;
        }

        public Criteria andNationIdNotEqualTo(String value) {
            addCriterion("nation_id <>", value, "nationId");
            return (Criteria) this;
        }

        public Criteria andNationIdGreaterThan(String value) {
            addCriterion("nation_id >", value, "nationId");
            return (Criteria) this;
        }

        public Criteria andNationIdGreaterThanOrEqualTo(String value) {
            addCriterion("nation_id >=", value, "nationId");
            return (Criteria) this;
        }

        public Criteria andNationIdLessThan(String value) {
            addCriterion("nation_id <", value, "nationId");
            return (Criteria) this;
        }

        public Criteria andNationIdLessThanOrEqualTo(String value) {
            addCriterion("nation_id <=", value, "nationId");
            return (Criteria) this;
        }

        public Criteria andNationIdLike(String value) {
            addCriterion("nation_id like", value, "nationId");
            return (Criteria) this;
        }

        public Criteria andNationIdNotLike(String value) {
            addCriterion("nation_id not like", value, "nationId");
            return (Criteria) this;
        }

        public Criteria andNationIdIn(List<String> values) {
            addCriterion("nation_id in", values, "nationId");
            return (Criteria) this;
        }

        public Criteria andNationIdNotIn(List<String> values) {
            addCriterion("nation_id not in", values, "nationId");
            return (Criteria) this;
        }

        public Criteria andNationIdBetween(String value1, String value2) {
            addCriterion("nation_id between", value1, value2, "nationId");
            return (Criteria) this;
        }

        public Criteria andNationIdNotBetween(String value1, String value2) {
            addCriterion("nation_id not between", value1, value2, "nationId");
            return (Criteria) this;
        }

        public Criteria andSexIsNull() {
            addCriterion("sex is null");
            return (Criteria) this;
        }

        public Criteria andSexIsNotNull() {
            addCriterion("sex is not null");
            return (Criteria) this;
        }

        public Criteria andSexEqualTo(String value) {
            addCriterion("sex =", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotEqualTo(String value) {
            addCriterion("sex <>", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexGreaterThan(String value) {
            addCriterion("sex >", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexGreaterThanOrEqualTo(String value) {
            addCriterion("sex >=", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLessThan(String value) {
            addCriterion("sex <", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLessThanOrEqualTo(String value) {
            addCriterion("sex <=", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLike(String value) {
            addCriterion("sex like", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotLike(String value) {
            addCriterion("sex not like", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexIn(List<String> values) {
            addCriterion("sex in", values, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotIn(List<String> values) {
            addCriterion("sex not in", values, "sex");
            return (Criteria) this;
        }

        public Criteria andSexBetween(String value1, String value2) {
            addCriterion("sex between", value1, value2, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotBetween(String value1, String value2) {
            addCriterion("sex not between", value1, value2, "sex");
            return (Criteria) this;
        }

        public Criteria andMassIdentityIsNull() {
            addCriterion("mass_identity is null");
            return (Criteria) this;
        }

        public Criteria andMassIdentityIsNotNull() {
            addCriterion("mass_identity is not null");
            return (Criteria) this;
        }

        public Criteria andMassIdentityEqualTo(String value) {
            addCriterion("mass_identity =", value, "massIdentity");
            return (Criteria) this;
        }

        public Criteria andMassIdentityNotEqualTo(String value) {
            addCriterion("mass_identity <>", value, "massIdentity");
            return (Criteria) this;
        }

        public Criteria andMassIdentityGreaterThan(String value) {
            addCriterion("mass_identity >", value, "massIdentity");
            return (Criteria) this;
        }

        public Criteria andMassIdentityGreaterThanOrEqualTo(String value) {
            addCriterion("mass_identity >=", value, "massIdentity");
            return (Criteria) this;
        }

        public Criteria andMassIdentityLessThan(String value) {
            addCriterion("mass_identity <", value, "massIdentity");
            return (Criteria) this;
        }

        public Criteria andMassIdentityLessThanOrEqualTo(String value) {
            addCriterion("mass_identity <=", value, "massIdentity");
            return (Criteria) this;
        }

        public Criteria andMassIdentityLike(String value) {
            addCriterion("mass_identity like", value, "massIdentity");
            return (Criteria) this;
        }

        public Criteria andMassIdentityNotLike(String value) {
            addCriterion("mass_identity not like", value, "massIdentity");
            return (Criteria) this;
        }

        public Criteria andMassIdentityIn(List<String> values) {
            addCriterion("mass_identity in", values, "massIdentity");
            return (Criteria) this;
        }

        public Criteria andMassIdentityNotIn(List<String> values) {
            addCriterion("mass_identity not in", values, "massIdentity");
            return (Criteria) this;
        }

        public Criteria andMassIdentityBetween(String value1, String value2) {
            addCriterion("mass_identity between", value1, value2, "massIdentity");
            return (Criteria) this;
        }

        public Criteria andMassIdentityNotBetween(String value1, String value2) {
            addCriterion("mass_identity not between", value1, value2, "massIdentity");
            return (Criteria) this;
        }

        public Criteria andIdentityNoIsNull() {
            addCriterion("identity_no is null");
            return (Criteria) this;
        }

        public Criteria andIdentityNoIsNotNull() {
            addCriterion("identity_no is not null");
            return (Criteria) this;
        }

        public Criteria andIdentityNoEqualTo(String value) {
            addCriterion("identity_no =", value, "identityNo");
            return (Criteria) this;
        }

        public Criteria andIdentityNoNotEqualTo(String value) {
            addCriterion("identity_no <>", value, "identityNo");
            return (Criteria) this;
        }

        public Criteria andIdentityNoGreaterThan(String value) {
            addCriterion("identity_no >", value, "identityNo");
            return (Criteria) this;
        }

        public Criteria andIdentityNoGreaterThanOrEqualTo(String value) {
            addCriterion("identity_no >=", value, "identityNo");
            return (Criteria) this;
        }

        public Criteria andIdentityNoLessThan(String value) {
            addCriterion("identity_no <", value, "identityNo");
            return (Criteria) this;
        }

        public Criteria andIdentityNoLessThanOrEqualTo(String value) {
            addCriterion("identity_no <=", value, "identityNo");
            return (Criteria) this;
        }

        public Criteria andIdentityNoLike(String value) {
            addCriterion("identity_no like", value, "identityNo");
            return (Criteria) this;
        }

        public Criteria andIdentityNoNotLike(String value) {
            addCriterion("identity_no not like", value, "identityNo");
            return (Criteria) this;
        }

        public Criteria andIdentityNoIn(List<String> values) {
            addCriterion("identity_no in", values, "identityNo");
            return (Criteria) this;
        }

        public Criteria andIdentityNoNotIn(List<String> values) {
            addCriterion("identity_no not in", values, "identityNo");
            return (Criteria) this;
        }

        public Criteria andIdentityNoBetween(String value1, String value2) {
            addCriterion("identity_no between", value1, value2, "identityNo");
            return (Criteria) this;
        }

        public Criteria andIdentityNoNotBetween(String value1, String value2) {
            addCriterion("identity_no not between", value1, value2, "identityNo");
            return (Criteria) this;
        }

        public Criteria andBirthdayIsNull() {
            addCriterion("birthday is null");
            return (Criteria) this;
        }

        public Criteria andBirthdayIsNotNull() {
            addCriterion("birthday is not null");
            return (Criteria) this;
        }

        public Criteria andBirthdayEqualTo(Date value) {
            addCriterionForJDBCDate("birthday =", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayNotEqualTo(Date value) {
            addCriterionForJDBCDate("birthday <>", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayGreaterThan(Date value) {
            addCriterionForJDBCDate("birthday >", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("birthday >=", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayLessThan(Date value) {
            addCriterionForJDBCDate("birthday <", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("birthday <=", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayIn(List<Date> values) {
            addCriterionForJDBCDate("birthday in", values, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayNotIn(List<Date> values) {
            addCriterionForJDBCDate("birthday not in", values, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("birthday between", value1, value2, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("birthday not between", value1, value2, "birthday");
            return (Criteria) this;
        }

        public Criteria andCollgeNoIsNull() {
            addCriterion("collge_no is null");
            return (Criteria) this;
        }

        public Criteria andCollgeNoIsNotNull() {
            addCriterion("collge_no is not null");
            return (Criteria) this;
        }

        public Criteria andCollgeNoEqualTo(String value) {
            addCriterion("collge_no =", value, "collgeNo");
            return (Criteria) this;
        }

        public Criteria andCollgeNoNotEqualTo(String value) {
            addCriterion("collge_no <>", value, "collgeNo");
            return (Criteria) this;
        }

        public Criteria andCollgeNoGreaterThan(String value) {
            addCriterion("collge_no >", value, "collgeNo");
            return (Criteria) this;
        }

        public Criteria andCollgeNoGreaterThanOrEqualTo(String value) {
            addCriterion("collge_no >=", value, "collgeNo");
            return (Criteria) this;
        }

        public Criteria andCollgeNoLessThan(String value) {
            addCriterion("collge_no <", value, "collgeNo");
            return (Criteria) this;
        }

        public Criteria andCollgeNoLessThanOrEqualTo(String value) {
            addCriterion("collge_no <=", value, "collgeNo");
            return (Criteria) this;
        }

        public Criteria andCollgeNoLike(String value) {
            addCriterion("collge_no like", value, "collgeNo");
            return (Criteria) this;
        }

        public Criteria andCollgeNoNotLike(String value) {
            addCriterion("collge_no not like", value, "collgeNo");
            return (Criteria) this;
        }

        public Criteria andCollgeNoIn(List<String> values) {
            addCriterion("collge_no in", values, "collgeNo");
            return (Criteria) this;
        }

        public Criteria andCollgeNoNotIn(List<String> values) {
            addCriterion("collge_no not in", values, "collgeNo");
            return (Criteria) this;
        }

        public Criteria andCollgeNoBetween(String value1, String value2) {
            addCriterion("collge_no between", value1, value2, "collgeNo");
            return (Criteria) this;
        }

        public Criteria andCollgeNoNotBetween(String value1, String value2) {
            addCriterion("collge_no not between", value1, value2, "collgeNo");
            return (Criteria) this;
        }

        public Criteria andMajorNoIsNull() {
            addCriterion("major_no is null");
            return (Criteria) this;
        }

        public Criteria andMajorNoIsNotNull() {
            addCriterion("major_no is not null");
            return (Criteria) this;
        }

        public Criteria andMajorNoEqualTo(String value) {
            addCriterion("major_no =", value, "majorNo");
            return (Criteria) this;
        }

        public Criteria andMajorNoNotEqualTo(String value) {
            addCriterion("major_no <>", value, "majorNo");
            return (Criteria) this;
        }

        public Criteria andMajorNoGreaterThan(String value) {
            addCriterion("major_no >", value, "majorNo");
            return (Criteria) this;
        }

        public Criteria andMajorNoGreaterThanOrEqualTo(String value) {
            addCriterion("major_no >=", value, "majorNo");
            return (Criteria) this;
        }

        public Criteria andMajorNoLessThan(String value) {
            addCriterion("major_no <", value, "majorNo");
            return (Criteria) this;
        }

        public Criteria andMajorNoLessThanOrEqualTo(String value) {
            addCriterion("major_no <=", value, "majorNo");
            return (Criteria) this;
        }

        public Criteria andMajorNoLike(String value) {
            addCriterion("major_no like", value, "majorNo");
            return (Criteria) this;
        }

        public Criteria andMajorNoNotLike(String value) {
            addCriterion("major_no not like", value, "majorNo");
            return (Criteria) this;
        }

        public Criteria andMajorNoIn(List<String> values) {
            addCriterion("major_no in", values, "majorNo");
            return (Criteria) this;
        }

        public Criteria andMajorNoNotIn(List<String> values) {
            addCriterion("major_no not in", values, "majorNo");
            return (Criteria) this;
        }

        public Criteria andMajorNoBetween(String value1, String value2) {
            addCriterion("major_no between", value1, value2, "majorNo");
            return (Criteria) this;
        }

        public Criteria andMajorNoNotBetween(String value1, String value2) {
            addCriterion("major_no not between", value1, value2, "majorNo");
            return (Criteria) this;
        }

        public Criteria andEnrollmentTimeIsNull() {
            addCriterion("enrollment_time is null");
            return (Criteria) this;
        }

        public Criteria andEnrollmentTimeIsNotNull() {
            addCriterion("enrollment_time is not null");
            return (Criteria) this;
        }

        public Criteria andEnrollmentTimeEqualTo(Date value) {
            addCriterionForJDBCDate("enrollment_time =", value, "enrollmentTime");
            return (Criteria) this;
        }

        public Criteria andEnrollmentTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("enrollment_time <>", value, "enrollmentTime");
            return (Criteria) this;
        }

        public Criteria andEnrollmentTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("enrollment_time >", value, "enrollmentTime");
            return (Criteria) this;
        }

        public Criteria andEnrollmentTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("enrollment_time >=", value, "enrollmentTime");
            return (Criteria) this;
        }

        public Criteria andEnrollmentTimeLessThan(Date value) {
            addCriterionForJDBCDate("enrollment_time <", value, "enrollmentTime");
            return (Criteria) this;
        }

        public Criteria andEnrollmentTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("enrollment_time <=", value, "enrollmentTime");
            return (Criteria) this;
        }

        public Criteria andEnrollmentTimeIn(List<Date> values) {
            addCriterionForJDBCDate("enrollment_time in", values, "enrollmentTime");
            return (Criteria) this;
        }

        public Criteria andEnrollmentTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("enrollment_time not in", values, "enrollmentTime");
            return (Criteria) this;
        }

        public Criteria andEnrollmentTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("enrollment_time between", value1, value2, "enrollmentTime");
            return (Criteria) this;
        }

        public Criteria andEnrollmentTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("enrollment_time not between", value1, value2, "enrollmentTime");
            return (Criteria) this;
        }

        public Criteria andGradeIsNull() {
            addCriterion("grade is null");
            return (Criteria) this;
        }

        public Criteria andGradeIsNotNull() {
            addCriterion("grade is not null");
            return (Criteria) this;
        }

        public Criteria andGradeEqualTo(String value) {
            addCriterion("grade =", value, "grade");
            return (Criteria) this;
        }

        public Criteria andGradeNotEqualTo(String value) {
            addCriterion("grade <>", value, "grade");
            return (Criteria) this;
        }

        public Criteria andGradeGreaterThan(String value) {
            addCriterion("grade >", value, "grade");
            return (Criteria) this;
        }

        public Criteria andGradeGreaterThanOrEqualTo(String value) {
            addCriterion("grade >=", value, "grade");
            return (Criteria) this;
        }

        public Criteria andGradeLessThan(String value) {
            addCriterion("grade <", value, "grade");
            return (Criteria) this;
        }

        public Criteria andGradeLessThanOrEqualTo(String value) {
            addCriterion("grade <=", value, "grade");
            return (Criteria) this;
        }

        public Criteria andGradeLike(String value) {
            addCriterion("grade like", value, "grade");
            return (Criteria) this;
        }

        public Criteria andGradeNotLike(String value) {
            addCriterion("grade not like", value, "grade");
            return (Criteria) this;
        }

        public Criteria andGradeIn(List<String> values) {
            addCriterion("grade in", values, "grade");
            return (Criteria) this;
        }

        public Criteria andGradeNotIn(List<String> values) {
            addCriterion("grade not in", values, "grade");
            return (Criteria) this;
        }

        public Criteria andGradeBetween(String value1, String value2) {
            addCriterion("grade between", value1, value2, "grade");
            return (Criteria) this;
        }

        public Criteria andGradeNotBetween(String value1, String value2) {
            addCriterion("grade not between", value1, value2, "grade");
            return (Criteria) this;
        }

        public Criteria andEducationalSystemIsNull() {
            addCriterion("educational_system is null");
            return (Criteria) this;
        }

        public Criteria andEducationalSystemIsNotNull() {
            addCriterion("educational_system is not null");
            return (Criteria) this;
        }

        public Criteria andEducationalSystemEqualTo(String value) {
            addCriterion("educational_system =", value, "educationalSystem");
            return (Criteria) this;
        }

        public Criteria andEducationalSystemNotEqualTo(String value) {
            addCriterion("educational_system <>", value, "educationalSystem");
            return (Criteria) this;
        }

        public Criteria andEducationalSystemGreaterThan(String value) {
            addCriterion("educational_system >", value, "educationalSystem");
            return (Criteria) this;
        }

        public Criteria andEducationalSystemGreaterThanOrEqualTo(String value) {
            addCriterion("educational_system >=", value, "educationalSystem");
            return (Criteria) this;
        }

        public Criteria andEducationalSystemLessThan(String value) {
            addCriterion("educational_system <", value, "educationalSystem");
            return (Criteria) this;
        }

        public Criteria andEducationalSystemLessThanOrEqualTo(String value) {
            addCriterion("educational_system <=", value, "educationalSystem");
            return (Criteria) this;
        }

        public Criteria andEducationalSystemLike(String value) {
            addCriterion("educational_system like", value, "educationalSystem");
            return (Criteria) this;
        }

        public Criteria andEducationalSystemNotLike(String value) {
            addCriterion("educational_system not like", value, "educationalSystem");
            return (Criteria) this;
        }

        public Criteria andEducationalSystemIn(List<String> values) {
            addCriterion("educational_system in", values, "educationalSystem");
            return (Criteria) this;
        }

        public Criteria andEducationalSystemNotIn(List<String> values) {
            addCriterion("educational_system not in", values, "educationalSystem");
            return (Criteria) this;
        }

        public Criteria andEducationalSystemBetween(String value1, String value2) {
            addCriterion("educational_system between", value1, value2, "educationalSystem");
            return (Criteria) this;
        }

        public Criteria andEducationalSystemNotBetween(String value1, String value2) {
            addCriterion("educational_system not between", value1, value2, "educationalSystem");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(String value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("status like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("status not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andEducationSystemIsNull() {
            addCriterion("education_system is null");
            return (Criteria) this;
        }

        public Criteria andEducationSystemIsNotNull() {
            addCriterion("education_system is not null");
            return (Criteria) this;
        }

        public Criteria andEducationSystemEqualTo(String value) {
            addCriterion("education_system =", value, "educationSystem");
            return (Criteria) this;
        }

        public Criteria andEducationSystemNotEqualTo(String value) {
            addCriterion("education_system <>", value, "educationSystem");
            return (Criteria) this;
        }

        public Criteria andEducationSystemGreaterThan(String value) {
            addCriterion("education_system >", value, "educationSystem");
            return (Criteria) this;
        }

        public Criteria andEducationSystemGreaterThanOrEqualTo(String value) {
            addCriterion("education_system >=", value, "educationSystem");
            return (Criteria) this;
        }

        public Criteria andEducationSystemLessThan(String value) {
            addCriterion("education_system <", value, "educationSystem");
            return (Criteria) this;
        }

        public Criteria andEducationSystemLessThanOrEqualTo(String value) {
            addCriterion("education_system <=", value, "educationSystem");
            return (Criteria) this;
        }

        public Criteria andEducationSystemLike(String value) {
            addCriterion("education_system like", value, "educationSystem");
            return (Criteria) this;
        }

        public Criteria andEducationSystemNotLike(String value) {
            addCriterion("education_system not like", value, "educationSystem");
            return (Criteria) this;
        }

        public Criteria andEducationSystemIn(List<String> values) {
            addCriterion("education_system in", values, "educationSystem");
            return (Criteria) this;
        }

        public Criteria andEducationSystemNotIn(List<String> values) {
            addCriterion("education_system not in", values, "educationSystem");
            return (Criteria) this;
        }

        public Criteria andEducationSystemBetween(String value1, String value2) {
            addCriterion("education_system between", value1, value2, "educationSystem");
            return (Criteria) this;
        }

        public Criteria andEducationSystemNotBetween(String value1, String value2) {
            addCriterion("education_system not between", value1, value2, "educationSystem");
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