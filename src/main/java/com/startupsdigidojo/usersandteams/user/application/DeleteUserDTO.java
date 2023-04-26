package com.startupsdigidojo.usersandteams.user.application;

import com.startupsdigidojo.usersandteams.user.domain.User;

public class DeleteUserDTO {

    private String name;
    private String mailAddress;
    private String password;

    public DeleteUserDTO() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public String getPassword() {
        return password;
    }

}
