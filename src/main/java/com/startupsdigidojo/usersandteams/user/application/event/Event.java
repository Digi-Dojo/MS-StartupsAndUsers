package com.startupsdigidojo.usersandteams.user.application.event;

import lombok.Getter;
import lombok.Setter;

public abstract class Event {
    @Setter
    @Getter
    protected String type;

    @Setter
    @Getter
    protected Object payload;
}
