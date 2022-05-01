package com.coetusstudio.hodanddeans.Models;

public class Users {
    String userImage, userName, userEmail, userBranch, userCourse, userSemester, userPassword, lastMessage;


    public Users(String userImage, String userName, String userEmail, String userBranch, String userCourse, String userSemester, String userPassword, String lastMessage) {
        this.userImage = userImage;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userBranch = userBranch;
        this.userCourse = userCourse;
        this.userSemester = userSemester;
        this.userPassword = userPassword;
        this.lastMessage = lastMessage;
    }

    public Users(String s){}

    public Users(String userImage, String userName, String userEmail, String userBranch, String userCourse, String userSemester, String userPassword) {
        this.userImage = userImage;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userBranch = userBranch;
        this.userCourse = userCourse;
        this.userSemester = userSemester;
        this.userPassword = userPassword;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserBranch() {
        return userBranch;
    }

    public void setUserBranch(String userBranch) {
        this.userBranch = userBranch;
    }

    public String getUserCourse() {
        return userCourse;
    }

    public void setUserCourse(String userCourse) {
        this.userCourse = userCourse;
    }

    public String getUserSemester() {
        return userSemester;
    }

    public void setUserSemester(String userSemester) {
        this.userSemester = userSemester;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}