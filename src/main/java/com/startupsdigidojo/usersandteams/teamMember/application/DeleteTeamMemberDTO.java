package com.startupsdigidojo.usersandteams.teamMember.application;

import com.startupsdigidojo.usersandteams.user.domain.User;

public class DeleteTeamMemberDTO {

    private Long id;

    DeleteTeamMemberDTO(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return id;
    }
}
