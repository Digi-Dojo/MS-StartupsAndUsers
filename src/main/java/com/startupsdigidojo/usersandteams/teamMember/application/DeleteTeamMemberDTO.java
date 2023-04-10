package com.startupsdigidojo.usersandteams.teamMember.application;

public class DeleteTeamMemberDTO {

    private final Long id;

    DeleteTeamMemberDTO(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
