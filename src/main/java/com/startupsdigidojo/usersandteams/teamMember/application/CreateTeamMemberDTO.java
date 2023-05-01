package com.startupsdigidojo.usersandteams.teamMember.application;

public class CreateTeamMemberDTO {

    Long userId;
    String role;
    Long startupId;

    public CreateTeamMemberDTO() {
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
