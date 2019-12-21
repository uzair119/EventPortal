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
@CrossOrigin
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

    @GetMapping(path=ADMIN_ONLY)
    public List<Competition> getAllDashboardCompetitions()
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
    public Competition getCompetitionDetailsAdmin(@PathVariable Long competitionId, HttpServletResponse httpServletResponse) throws IOException {
        Competition competition = competitionService.getCompetitionById(competitionId);
        if(competition == null)
            httpServletResponse.sendError(404);
        return competition;
    }

    @GetMapping(path=CONTROLLER_PREFIX+"/{competitionId}")
    public Competition getCompetitionDetailsAll(@PathVariable Long competitionId, HttpServletResponse httpServletResponse) throws IOException {
        Competition competition = competitionService.getCompetitionById(competitionId);
        if(competition == null)
            httpServletResponse.sendError(404);
        competition.setTeams(null);
        return competition;
    }

    @GetMapping(path=CONTROLLER_PREFIX)
    public List<Competition> getAllCompetitions()
    {
        List<Competition> list =  competitionService.getAllCompetitions();
        list.forEach(competition -> competition.setTeams(null));
        return list;
    }

//
//    @GetMapping(path=CONTROLLER_PREFIX+"/{name}")
//    public List<Competition> getCompetitionsByName(@PathVariable String name)
//    {
//        return competitionService.getAllCompetitionsByName(name);
//    }


    @GetMapping(path=CONTROLLER_PREFIX+"/maxteamsize/{maxteamsize}")
    public List<Competition> getAllCompetitions(@PathVariable Integer maxteamsize)
    {
        return competitionService.getAllCompetitionsByMaxTeamSize(maxteamsize);
    }

    @PutMapping(path=ADMIN_ONLY+"/{competitionId}")
    public Competition updateCompetition(@RequestBody Competition competition, @PathVariable Long competitionId, HttpServletResponse httpServletResponse) throws IOException {
        Competition c = competitionService.getCompetitionById(competitionId);
        if(c == null)
            httpServletResponse.sendError(404);
        competition.setId(competitionId);
        return competitionService.save(competition);
    }

    @DeleteMapping(path=ADMIN_ONLY+"/{competitionId}")
    public void deleteCompetition(@PathVariable Long competitionId, HttpServletResponse httpServletResponse) throws IOException {
        if(competitionService.existsById(competitionId))
            competitionService.deleteCompetition(competitionId);
        else
            httpServletResponse.sendError(404);
    }

}
