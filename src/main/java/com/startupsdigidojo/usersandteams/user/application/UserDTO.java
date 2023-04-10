package com.startupsdigidojo.usersandteams.user.application;

public class UserDTO {

    String name;

    String password;

    String mailAddress;

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

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }


    public UserDTO(String name, String password, String mailAddress) {
        this.name = name;
        this.password = password;
        this.mailAddress = mailAddress;
    }
}
