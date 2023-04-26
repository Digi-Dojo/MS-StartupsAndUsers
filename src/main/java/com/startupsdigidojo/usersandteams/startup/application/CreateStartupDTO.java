package com.startupsdigidojo.usersandteams.startup.application;

public class CreateStartupDTO {
    private String name;
    private String description;

    public CreateStartupDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
