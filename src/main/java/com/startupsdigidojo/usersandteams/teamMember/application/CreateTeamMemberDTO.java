package com.startupsdigidojo.usersandteams.teamMember.application;
import com.startupsdigidojo.usersandteams.user.domain.User;

public class CreateTeamMemberDTO {

    User user;
    String role;

    public CreateTeamMemberDTO() {
    }

    public CreateTeamMemberDTO(User user, String role) {
        this.user = user;
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public String getRole() {
        return role;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
