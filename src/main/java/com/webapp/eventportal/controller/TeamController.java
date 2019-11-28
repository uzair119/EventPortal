package com.webapp.eventportal.controller;

import com.webapp.eventportal.model.Team;
import com.webapp.eventportal.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/auth/team")
@RestController
public class TeamController {

    @Autowired
    TeamService teamService;

    @PostMapping(value="/register")
    public Team register(@RequestBody Team team)
    {
        return teamService.save(team);
    }

    @GetMapping(value = "")
    public List<Team> getAllTeams() {
        return teamService.getAllTeams();
    }

    @GetMapping(value = "/{name}")
    public List<Team> getAllTeamsByName(@PathVariable String name) {
        return teamService.getAllTeamsByName(name);
    }

    @GetMapping(value = "/institution/{id}")
    public List<Team> getAllTeamsByInstitution(@PathVariable Long id) {
        return teamService.getAllTeamsByInstitutionId(id);
    }

    @GetMapping(value = "/lead/{id}")
    public List<Team> getAllTeamsByName(@PathVariable Long id) {
        return teamService.getAllTeamsByTeamLeadId(id);
    }

    @DeleteMapping(value="{id}")
    public void deleteTeam(@PathVariable Long id)
    {
        teamService.deleteTeam(id);
    }
}

