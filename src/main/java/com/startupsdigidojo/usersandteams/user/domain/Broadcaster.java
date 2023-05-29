package com.startupsdigidojo.usersandteams.user.domain;

public interface Broadcaster {
    void emitNewUser(User user);
}
