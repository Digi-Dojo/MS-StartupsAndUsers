package com.startupsdigidojo.usersandteams.teamMember.application.event;

import com.startupsdigidojo.usersandteams.teamMember.domain.TeamMember;
import lombok.Getter;

public class StartupAddedUser extends TeamMemberEvent{
    private final String STARTUP_ADDED_USER = "Startup Added User";

    @Getter
    private String type = STARTUP_ADDED_USER;

    @Getter
    private TeamMember payload;

    public StartupAddedUser(TeamMember payload) {
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
