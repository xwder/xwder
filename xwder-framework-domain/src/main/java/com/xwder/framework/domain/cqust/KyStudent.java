package com.xwder.framework.domain.cqust;

import java.util.Date;

public class KyStudent {
    private Integer id;

    private String studentNo;

    private String studentName;

    private String nationId;

    private String sex;

    private String massIdentity;

    private String identityNo;

    private Date birthday;

    private String collgeNo;

    private String majorNo;

    private Date enrollmentTime;

    private String grade;

    private String educationalSystem;

    private String status;

    private String educationSystem;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo == null ? null : studentNo.trim();
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName == null ? null : studentName.trim();
    }

    public String getNationId() {
        return nationId;
    }

    public void setNationId(String nationId) {
        this.nationId = nationId == null ? null : nationId.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getMassIdentity() {
        return massIdentity;
    }

    public void setMassIdentity(String massIdentity) {
        this.massIdentity = massIdentity == null ? null : massIdentity.trim();
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo == null ? null : identityNo.trim();
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getCollgeNo() {
        return collgeNo;
    }

    public void setCollgeNo(String collgeNo) {
        this.collgeNo = collgeNo == null ? null : collgeNo.trim();
    }

    public String getMajorNo() {
        return majorNo;
    }

    public void setMajorNo(String majorNo) {
        this.majorNo = majorNo == null ? null : majorNo.trim();
    }

    public Date getEnrollmentTime() {
        return enrollmentTime;
    }

    public void setEnrollmentTime(Date enrollmentTime) {
        this.enrollmentTime = enrollmentTime;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade == null ? null : grade.trim();
    }

    public String getEducationalSystem() {
        return educationalSystem;
    }

    public void setEducationalSystem(String educationalSystem) {
        this.educationalSystem = educationalSystem == null ? null : educationalSystem.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getEducationSystem() {
        return educationSystem;
    }

    public void setEducationSystem(String educationSystem) {
        this.educationSystem = educationSystem == null ? null : educationSystem.trim();
    }
}