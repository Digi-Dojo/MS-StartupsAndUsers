package com.startupsdigidojo.usersandteams.user.application;

public class UpdatePasswordDTO {

    String mailAddress;

    String oldPassword;

    String newPassword;

    public UpdatePasswordDTO(String mailAddress, String oldPassword, String newPassword) {
        this.mailAddress = mailAddress;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }


    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }



}
