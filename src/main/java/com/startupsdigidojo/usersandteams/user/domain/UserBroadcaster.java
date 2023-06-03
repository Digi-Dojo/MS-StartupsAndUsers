package com.startupsdigidojo.usersandteams.user.domain;

public interface UserBroadcaster {
    void emitNewUser(User user);

    void emitUserLogIn(User user);
}
