package com.coetusstudio.hodanddeans.Models.Admin;

public class User {

    String userImage, userName, userEmail, userID, userPosition, userSchool, userPassword, userUid;

    public User(String userImage, String userName, String userEmail, String userID, String userPosition, String userSchool, String userPassword, String userUid) {
        this.userImage = userImage;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userID = userID;
        this.userPosition = userPosition;
        this.userSchool = userSchool;
        this.userPassword = userPassword;
        this.userUid = userUid;
    }

    public User() {
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

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserPosition() {
        return userPosition;
    }

    public void setUserPosition(String userPosition) {
        this.userPosition = userPosition;
    }

    public String getUserSchool() {
        return userSchool;
    }

    public void setUserSchool(String userSchool) {
        this.userSchool = userSchool;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }
}
