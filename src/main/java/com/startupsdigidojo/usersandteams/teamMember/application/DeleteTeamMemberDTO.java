package com.startupsdigidojo.usersandteams.teamMember.application;

public class DeleteTeamMemberDTO {

    private Long id;

    public DeleteTeamMemberDTO() {
    }

    public DeleteTeamMemberDTO(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
