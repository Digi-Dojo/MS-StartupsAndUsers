package com.startupsdigidojo.usersandteams.teamMember.application;

public class FindTeamMemberByUserIdAndStartupIdDTO {

    private Long userId;
    private Long startupId;

    public FindTeamMemberByUserIdAndStartupIdDTO() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getStartupId() {
        return startupId;
    }

    public void setStartupId(Long startupId) {
        this.startupId = startupId;
    }
}
