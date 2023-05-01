package com.startupsdigidojo.usersandteams.startup.application;

public class UpdateStartupNameDTO {

    private String oldName;
    private String newName;

    public UpdateStartupNameDTO() {
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }
}
