package com.startupsdigidojo.usersandteams.teamMember.application;
import com.startupsdigidojo.usersandteams.startup.domain.Startup;
import com.startupsdigidojo.usersandteams.user.domain.User;

public class CreateTeamMemberDTO {

    User user;
    String role;
    Startup startup;

    public CreateTeamMemberDTO() {
    }

    public CreateTeamMemberDTO(User user, String role, Startup startup) {
        this.user = user;
        this.role = role;
        this.startup = startup;
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

    public Startup getStartup() {
        return startup;
    }

    public void setStartup(Startup startup) {
        this.startup = startup;
    }
}
