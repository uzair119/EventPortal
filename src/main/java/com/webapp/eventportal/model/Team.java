package com.webapp.eventportal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name="team_lead_id",nullable =false)
    private User teamLead;

    @ManyToOne
    @JoinColumn()
    private Institution institution;

    @OneToMany(mappedBy ="team", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<TeamMember> teamMembers = new HashSet();

    @ManyToMany(mappedBy = "teams")
    @JsonIgnore
    private Set<Competition> competitions = new HashSet();

    public Set<Competition> getCompetitions() {
        return competitions;
    }

    public void setCompetitions(Set<Competition> competitions) {
        this.competitions = competitions;
    }

    public Set<TeamMember> getTeamMemberList() {
        return teamMembers;
    }

    public void setTeamMemberList(Set<TeamMember> teamMembers) {
        this.teamMembers = teamMembers;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getTeamLead() {
        return teamLead;
    }

    public void setTeamLead(User teamLead) {
        this.teamLead = teamLead;
    }

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }
}
