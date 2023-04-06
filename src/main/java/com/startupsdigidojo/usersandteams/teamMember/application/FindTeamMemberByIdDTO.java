package com.startupsdigidojo.usersandteams.teamMember.application;

public class FindTeamMemberByIdDTO {

    private Long id;

    FindTeamMemberByIdDTO(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
