package com.startupsdigidojo.usersandteams.application.TeamMember;
import com.startupsdigidojo.usersandteams.domain.User.User;

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
