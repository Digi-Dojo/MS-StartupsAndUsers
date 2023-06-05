package com.startupsdigidojo.usersandteams.teamMember.application.event;

import com.startupsdigidojo.usersandteams.teamMember.domain.TeamMember;
import lombok.Getter;

public class StartupRemovedUser extends TeamMemberEvent{
    private final String STARTUP_REMOVED_USER = "Startup Removed User";

    @Getter
    private String type = STARTUP_REMOVED_USER;

    @Getter
    private TeamMember payload;

    public StartupRemovedUser(TeamMember payload) {
        this.payload = payload;
    }

    public String toJson(){
        return "{" +
                "\"type\": \"" + type + "\"," +
                "\"payload\": {" +
                "\"id\": \"" + payload.getId() + "\"," +
                "\"puser\": \"" + payload.getPuser().getId() + "\"," +
                "\"startup\": \"" + payload.getStartup().getId() + "\"," +
                "\"role\": \"" + payload.getRole() + "\"," +
                "\"time\": \"" + System.currentTimeMillis() + "\"" +
                "}" +
                "}";
    }
}
