package com.webapp.eventportal.service;

import com.webapp.eventportal.model.Institution;
import com.webapp.eventportal.model.Team;
import com.webapp.eventportal.model.User;
import com.webapp.eventportal.repository.TeamRepository;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {

    @Autowired
    TeamRepository teamRepository;

    public Team save(Team team)
    {
        return teamRepository.save(team);
    }

    public Team getTeamById(Long id)
    {
        Optional<Team> team = teamRepository.findById(id);
        return team.orElse(null);
    }

    public List<Team> getAllTeams(){
        return IterableUtils.toList(teamRepository.findAll());
    }

    public List<Team> getAllTeamsByName(String name){
        return IterableUtils.toList(teamRepository.findAllByName(name));
    }

    public List<Team> getAllTeamsByInstitutionId(Long id)
    {
        return IterableUtils.toList(teamRepository.findAllByInstitution_Id(id));
    }

    public List<Team> getAllTeamsByTeamLeadId(Long id)
    {
        return IterableUtils.toList(teamRepository.findAllByTeamLead_Id(id));
    }

    public List<Team> getAllTeamsByInstitutionId(Institution institution)
    {
        return IterableUtils.toList(teamRepository.findAllByInstitution(institution));
    }

    public List<Team> getAllTeamsByTeamLead(User teamLead)
    {
        return IterableUtils.toList(teamRepository.findAllByTeamLead(teamLead));
    }

    public void deleteTeam(Long id)
    {
        teamRepository.deleteById(id);
    }
}
