package com.startupsdigidojo.usersandteams.teamMember.domain;


import com.startupsdigidojo.usersandteams.startup.domain.Startup;
import com.startupsdigidojo.usersandteams.user.domain.User;
import jakarta.persistence.*;

@Entity
public class TeamMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User puser;

    @OneToOne
    private Startup startup;

    private String role;

    public TeamMember() {
    }

    public TeamMember(User puser, String role, Startup startup) {
        this.puser = puser;
        this.role = role;
        this.startup = startup;
    }

    public TeamMember(Long id, User puser, String role, Startup startup) {
        this.id = id;
        this.puser = puser;
        this.role = role;
        this.startup = startup;
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

    public Startup getStartup() {
        return startup;
    }

    public void setStartup(Startup startup) {
        this.startup = startup;
    }
}
