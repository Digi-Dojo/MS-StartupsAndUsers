package com.startupsdigidojo.usersandteams.teamMember.application;

public class FindTeamMemberByRoleDTO {

    private String role;

    public FindTeamMemberByRoleDTO() {
    }

    public FindTeamMemberByRoleDTO(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
