package com.startupsdigidojo.usersandteams.domain.Teams;


import com.startupsdigidojo.usersandteams.domain.User.User;
import jakarta.persistence.*;

@Entity
public class TeamMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamMemberID;

    @OneToMany
    private User user;

    private String role;

    public TeamMember() {
    }

    public TeamMember(Long teamMemberID, User user, String role){
        this.teamMemberID = teamMemberID;
        this.user = user;
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getTeamMemberID() {
        return teamMemberID;
    }

    public String getRole() {
        return role;
    }

    public void setTeamMemberID(Long teamMemberID) {
        this.teamMemberID = teamMemberID;
    }

    public void setRole(String role) {
        this.role = role;
    }
}