package com.startupsdigidojo.usersandteams.domain.Teams;


import com.startupsdigidojo.usersandteams.domain.User.User;
import jakarta.persistence.*;

@Entity
public class TeamMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamMemberId;

    @OneToOne
    private User puser;

    private String role;

    public TeamMember() {
    }

    public TeamMember(User puser, String role){
        this.puser = puser;
        this.role = role;
    }

    public User getPuser() {
        return puser;
    }

    public void setPuser(User user) {
        this.puser = user;
    }

    public Long getTeamMemberId() {
        return teamMemberId;
    }

    public String getRole() {
        return role;
    }

    public void setTeamMemberId(Long teamMemberID) {
        this.teamMemberId = teamMemberID;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
