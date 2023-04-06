package com.startupsdigidojo.usersandteams.user.application;

import com.startupsdigidojo.usersandteams.user.domain.User;

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
        this.password = User.hashPassword(password);
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }


    public UserDTO(String name, String password, String mailAddress) {
        this.name = name;
        this.password = User.hashPassword(password);
        this.mailAddress = mailAddress;
    }
}
