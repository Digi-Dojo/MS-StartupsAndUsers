package com.startupsdigidojo.usersandteams.startup.domain;

import jakarta.persistence.*;

@Entity
public class Startup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private String description;


    public Startup() {
    }

    public Startup(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Startup(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
