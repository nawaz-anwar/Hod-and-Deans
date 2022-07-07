package com.coetusstudio.hodanddeans.Models;

public class Subject {

    String subject, subjectCode, id;

    public Subject(String subject, String subjectCode, String id) {
        this.subject = subject;
        this.subjectCode = subjectCode;
        this.id = id;
    }

    public Subject() {
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
