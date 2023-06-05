package com.startupsdigidojo.usersandteams.teamMember.domain;

public interface TeamMemberBroadcaster {
    void emitNewTeamMember(TeamMember teamMember);
}
