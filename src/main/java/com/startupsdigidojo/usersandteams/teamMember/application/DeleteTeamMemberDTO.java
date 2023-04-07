package com.startupsdigidojo.usersandteams.teamMember.application;

public class DeleteTeamMemberDTO {

    private Long id;

    DeleteTeamMemberDTO(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
