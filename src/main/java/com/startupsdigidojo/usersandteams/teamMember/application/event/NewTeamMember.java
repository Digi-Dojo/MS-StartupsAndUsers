package com.startupsdigidojo.usersandteams.teamMember.application.event;

import com.startupsdigidojo.usersandteams.teamMember.domain.TeamMember;
import lombok.Getter;

public class NewTeamMember extends TeamMemberEvent{
    private final String NEW_TEAM_MEMBER = "New Team Member";

    @Getter
    private String type = NEW_TEAM_MEMBER;

    @Getter
    private TeamMember payload;

    public NewTeamMember(TeamMember payload) {
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
