package com.startupsdigidojo.usersandteams.application.User;

public class UserDTO {

    String name;

    String password;

    String mailAddres;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMailAddres() {
        return mailAddres;
    }

    public void setMailAddres(String mailAddres) {
        this.mailAddres = mailAddres;
    }


    public UserDTO(String name, String password, String mailAddres) {
        this.name = name;
        this.password = password;
        this.mailAddres = mailAddres;
    }
}
