package com.startupsdigidojo.usersandteams.user.domain;

public interface UserBroadcaster {
    void emitUserCreated(User user);

    void emitUserUpdated(User user, String attribute);

    void emitUserDeleted(User user);

    void emitUserLogIn(User user);
}
