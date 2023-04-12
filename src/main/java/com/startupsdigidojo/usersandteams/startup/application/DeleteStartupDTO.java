package com.startupsdigidojo.usersandteams.startup.application;

public class DeleteStartupDTO {

    private String name;

    public DeleteStartupDTO() {}

    public DeleteStartupDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
