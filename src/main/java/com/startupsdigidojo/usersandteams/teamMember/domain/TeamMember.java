package com.startupsdigidojo.usersandteams.teamMember.domain;


import com.startupsdigidojo.usersandteams.user.domain.User;
import jakarta.persistence.*;

@Entity
public class TeamMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User puser;

    private String role;

    public TeamMember() {
    }

    public TeamMember(User puser, String role){
        this.puser = puser;
        this.role = role;
    }

    public TeamMember(Long id, User puser, String role) {
        this.id = id;
        this.puser = puser;
        this.role = role;
    }

    public User getPuser() {
        return puser;
    }

    public void setPuser(User user) {
        this.puser = user;
    }

    public Long getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public void setId(Long teamMemberID) {
        this.id = teamMemberID;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
