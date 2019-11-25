package com.webapp.eventportal.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class User {
    @Id
    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private String firstname;

    @Column(nullable = true)
    private String lastname;

    @Column(nullable = true)
    private String email;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false)
    private Date dateCreate = new Date();

    public boolean getActive() {
        return active;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstname() {
        return firstname;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

