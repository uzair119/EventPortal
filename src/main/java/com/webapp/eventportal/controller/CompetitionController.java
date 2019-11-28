package com.webapp.eventportal.controller;

import com.webapp.eventportal.model.Competition;
import com.webapp.eventportal.model.Team;
import com.webapp.eventportal.service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CompetitionController {

    static final String ADMIN_PREFIX = "/auth/admin";
    static final String AUTH_PREFIX = "/auth";
    static final String CONTROLLER_PREFIX = "/competition";

    static final String ADMIN_ONLY = ADMIN_PREFIX+CONTROLLER_PREFIX;
    static final String AUTH_ONLY = AUTH_PREFIX+CONTROLLER_PREFIX;

    @Autowired
    CompetitionService competitionService;

    @PostMapping(path = ADMIN_ONLY+"/create")
    public Competition save(@RequestBody Competition competition)
    {
        return competitionService.save(competition);
    }

    @GetMapping(path=CONTROLLER_PREFIX)
    public List<Competition> getAllCompetitions()
    {
        return competitionService.getAllCompetitions();
    }

    @PostMapping(path=AUTH_ONLY+ "/{competitionId}/register")
    public Competition registerTeam(@RequestBody Team team, @PathVariable Long competitionId, HttpServletResponse httpServletResponse) throws IOException {
        Competition competition = competitionService.getCompetitionById(competitionId);
        if(competition == null)
            httpServletResponse.sendError(404);
        competition.getTeams().add(team);
        return competitionService.save(competition);
    }

    @GetMapping(path=ADMIN_ONLY+"/{competitionId}")
    public Competition getCompetitionDetails(@PathVariable Long competitionId, HttpServletResponse httpServletResponse) throws IOException {
        Competition competition = competitionService.getCompetitionById(competitionId);
        if(competition == null)
            httpServletResponse.sendError(404);
        return competition;
    }


    @GetMapping(path=CONTROLLER_PREFIX+"/{name}")
    public List<Competition> getAllCompetitions(@PathVariable String name)
    {
        return competitionService.getAllCompetitionsByName(name);
    }


    @GetMapping(path=CONTROLLER_PREFIX+"/maxteamsize/{maxteamsize}")
    public List<Competition> getAllCompetitions(@PathVariable Integer maxteamsize)
    {
        return competitionService.getAllCompetitionsByMaxTeamSize(maxteamsize);
    }

}
