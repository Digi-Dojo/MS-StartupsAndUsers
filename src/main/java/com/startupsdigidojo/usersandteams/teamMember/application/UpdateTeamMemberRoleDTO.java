package com.startupsdigidojo.usersandteams.teamMember.application;

public class UpdateTeamMemberRoleDTO {

    private Long id;
    private String oldRole;
    private String newRole;

    public UpdateTeamMemberRoleDTO(Long id, String oldRole, String newRole) {
        this.id = id;
        this.oldRole = oldRole;
        this.newRole = newRole;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOldRole() {
        return oldRole;
    }

    public void setOldRole(String oldRole) {
        this.oldRole = oldRole;
    }

    public String getNewRole() {
        return newRole;
    }

    public void setNewRole(String newRole) {
        this.newRole = newRole;
    }
}
