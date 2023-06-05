package com.startupsdigidojo.usersandteams.teamMember.application.event;

import lombok.Getter;
import lombok.Setter;

public abstract class TeamMemberEvent {
    @Setter @Getter
    private String type;

    @Setter @Getter
    private Object payload;
}
