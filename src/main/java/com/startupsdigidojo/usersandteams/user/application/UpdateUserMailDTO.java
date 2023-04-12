package com.startupsdigidojo.usersandteams.user.application;

public class UpdateUserMailDTO {

    private String oldMail;
    private String newMail;

    UpdateUserMailDTO(String oldMail, String newMail) {
        this.oldMail = oldMail;
        this.newMail = newMail;
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
