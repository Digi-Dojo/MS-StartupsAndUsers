package com.startupsdigidojo.usersandteams.teamMember.application;
import com.startupsdigidojo.usersandteams.startup.domain.Startup;
import com.startupsdigidojo.usersandteams.user.domain.User;

public class CreateTeamMemberDTO {

    Long userId;
    String role;
    Long startupId;

    public CreateTeamMemberDTO() {
    }

    public CreateTeamMemberDTO(Long userId, String role, Long startupId) {
        this.userId = userId;
        this.role = role;
        this.startupId = startupId;
    }

    public Long getUserId() {
        return userId;
    }

    public String getRole() {
        return role;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getStartupId() {
        return startupId;
    }

    public void setStartupId(Long startupId) {
        this.startupId = startupId;
    }
}
