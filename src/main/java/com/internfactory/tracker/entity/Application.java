
package com.internfactory.tracker.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "applications")
public class Application {

    @Id
    @Column(name = "application_id")
    private String applicationId;

    private String submittedAt;
    private String fullName;
    private String email;
    private String mobile;
    private String college;
    private String degreeProgram;
    private String year;
    private String preferredDomain;

    @Column(length = 1000)
    private String technicalSkills;

    @Column(length = 1000)
    private String resumeLink;

    @Column(columnDefinition = "TEXT")
    private String statementOfInterest;

    private String status = "NEW";

    public Application() {
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(String submittedAt) {
        this.submittedAt = submittedAt;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getDegreeProgram() {
        return degreeProgram;
    }

    public void setDegreeProgram(String degreeProgram) {
        this.degreeProgram = degreeProgram;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPreferredDomain() {
        return preferredDomain;
    }

    public void setPreferredDomain(String preferredDomain) {
        this.preferredDomain = preferredDomain;
    }

    public String getTechnicalSkills() {
        return technicalSkills;
    }

    public void setTechnicalSkills(String technicalSkills) {
        this.technicalSkills = technicalSkills;
    }

    public String getResumeLink() {
        return resumeLink;
    }

    public void setResumeLink(String resumeLink) {
        this.resumeLink = resumeLink;
    }

    public String getStatementOfInterest() {
        return statementOfInterest;
    }

    public void setStatementOfInterest(String statementOfInterest) {
        this.statementOfInterest = statementOfInterest;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
