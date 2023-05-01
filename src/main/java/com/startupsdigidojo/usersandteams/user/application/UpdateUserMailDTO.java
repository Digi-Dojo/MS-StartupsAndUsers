package com.startupsdigidojo.usersandteams.user.application;

public class UpdateUserMailDTO {

    private String oldMail;
    private String newMail;

    public UpdateUserMailDTO() {
    }

    public String getOldMail() {
        return oldMail;
    }

    public void setOldMail(String oldMail) {
        this.oldMail = oldMail;
    }

    public String getNewMail() {
        return newMail;
    }

    public void setNewMail(String newMail) {
        this.newMail = newMail;
    }
}
