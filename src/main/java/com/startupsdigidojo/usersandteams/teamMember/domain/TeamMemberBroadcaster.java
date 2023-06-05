package com.startupsdigidojo.usersandteams.teamMember.domain;

public interface TeamMemberBroadcaster {
    void emitStartupAddedUser(TeamMember teamMember);

    void emitStartupRemovedUser(TeamMember teamMember);
}
