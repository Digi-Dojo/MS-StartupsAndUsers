package com.startupsdigidojo.usersandteams.startup.application;

public class CreateStartupDTO {
    private String name;
    /*private String newName;
    private String oldName;*/
    private String description;

    public CreateStartupDTO() {
    }

    public CreateStartupDTO(String name, String description) {
        this.name = name;
        this.description = description;
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

    /*public String getNewName(){return newName;}

    public void setNewName(String newName) {this.newName = newName;}

    public String getOldName() {return oldName;}

    public void setOldName(String oldName) {this.oldName = oldName;}*/


}
