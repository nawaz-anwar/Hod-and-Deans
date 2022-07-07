package com.coetusstudio.hodanddeans.Models;

public class Accountdata {


    String imageAccount, nameAccount, emailAccount, idAccount, positionAccount, passwordAccount, messageAccount;


    public Accountdata(String imageAccount, String nameAccount, String emailAccount, String idAccount, String positionAccount, String passwordAccount, String messageAccount) {
        this.imageAccount = imageAccount;
        this.nameAccount = nameAccount;
        this.emailAccount = emailAccount;
        this.idAccount = idAccount;
        this.positionAccount = positionAccount;
        this.passwordAccount = passwordAccount;
        this.messageAccount = messageAccount;
    }

    public Accountdata() {
    }

    public Accountdata(String imageAccount, String nameAccount, String emailAccount, String idAccount, String positionAccount, String passwordAccount) {
        this.imageAccount = imageAccount;
        this.nameAccount = nameAccount;
        this.emailAccount = emailAccount;
        this.idAccount = idAccount;
        this.positionAccount = positionAccount;
        this.passwordAccount = passwordAccount;
    }

    public String getImageAccount() {
        return imageAccount;
    }

    public void setImageAccount(String imageAccount) {
        this.imageAccount = imageAccount;
    }

    public String getNameAccount() {
        return nameAccount;
    }

    public void setNameAccount(String nameAccount) {
        this.nameAccount = nameAccount;
    }

    public String getEmailAccount() {
        return emailAccount;
    }

    public void setEmailAccount(String emailAccount) {
        this.emailAccount = emailAccount;
    }

    public String getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(String idAccount) {
        this.idAccount = idAccount;
    }

    public String getPositionAccount() {
        return positionAccount;
    }

    public void setPositionAccount(String positionAccount) {
        this.positionAccount = positionAccount;
    }

    public String getPasswordAccount() {
        return passwordAccount;
    }

    public void setPasswordAccount(String passwordAccount) {
        this.passwordAccount = passwordAccount;
    }

    public String getMessageAccount() {
        return messageAccount;
    }

    public void setMessageAccount(String messageAccount) {
        this.messageAccount = messageAccount;
    }
}
