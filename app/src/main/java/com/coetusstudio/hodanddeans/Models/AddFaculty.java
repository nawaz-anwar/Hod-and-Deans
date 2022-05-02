package com.coetusstudio.hodanddeans.Models;

public class AddFaculty {
    private static String facultyImage;
    String facultyName, facultyEmail, facultyId, facultySubject, facultySubjectCode, facultyPassword, facultyMessage;


    public AddFaculty(String facultyImage, String facultyName, String facultyEmail, String facultyId, String facultySubject, String facultySubjectCode, String facultyPassword, String facultyMessage) {
        this.facultyImage = facultyImage;
        this.facultyName = facultyName;
        this.facultyEmail = facultyEmail;
        this.facultyId = facultyId;
        this.facultySubject = facultySubject;
        this.facultySubjectCode = facultySubjectCode;
        this.facultyPassword = facultyPassword;
        this.facultyMessage = facultyMessage;
    }
    public AddFaculty(){}
    public AddFaculty(String facultyImage, String facultyName, String facultyEmail, String facultyId, String facultySubject, String facultySubjectCode, String facultyPassword) {
        this.facultyImage = facultyImage;
        this.facultyName = facultyName;
        this.facultyEmail = facultyEmail;
        this.facultyId = facultyId;
        this.facultySubject = facultySubject;
        this.facultySubjectCode = facultySubjectCode;
        this.facultyPassword = facultyPassword;
    }

    public static String getFacultyImage() {
        return facultyImage;
    }

    public void setFacultyImage(String facultyImage) {
        this.facultyImage = facultyImage;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getFacultyEmail() {
        return facultyEmail;
    }

    public void setFacultyEmail(String facultyEmail) {
        this.facultyEmail = facultyEmail;
    }

    public String getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(String facultyId) {
        this.facultyId = facultyId;
    }

    public String getFacultySubject() {
        return facultySubject;
    }

    public void setFacultySubject(String facultySubject) {
        this.facultySubject = facultySubject;
    }

    public String getFacultySubjectCode() {
        return facultySubjectCode;
    }

    public void setFacultySubjectCode(String facultySubjectCode) {
        this.facultySubjectCode = facultySubjectCode;
    }

    public String getFacultyPassword() {
        return facultyPassword;
    }

    public void setFacultyPassword(String facultyPassword) {
        this.facultyPassword = facultyPassword;
    }

    public String getFacultyMessage() {
        return facultyMessage;
    }

    public void setFacultyMessage(String facultyMessage) {
        this.facultyMessage = facultyMessage;
    }
}
