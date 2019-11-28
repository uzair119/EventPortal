package com.webapp.eventportal.service;

import com.webapp.eventportal.model.Competition;
import com.webapp.eventportal.repository.CompetitionRepository;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompetitionService {
    @Autowired
    CompetitionRepository competitionRepository;

//    @Autowired
//    TeamCompetitionRepository teamCompetitionRepository;

    public Competition save(Competition competition)
    {
        return competitionRepository.save(competition);
    }

//    public TeamCompetitions register(TeamCompetitions teamCompetitions)
//    {
//        return teamCompetitionRepository.save(teamCompetitions);
//    }
//
//    public TeamCompetitions getTeamCompetition(Competition competition, Team team)
//    {
//        return teamCompetitionRepository.findByCompetitionAndTeam(competition,team);
//    }

    public Competition getCompetitionById(Long id)
    {
        Optional<Competition> competition = competitionRepository.findById(id);
        return competition.orElse(null);
    }

    public List<Competition> getAllCompetitions()
    {
        return IterableUtils.toList(competitionRepository.findAll());
    }

    public List<Competition> getAllCompetitionsByName(String name)
    {
        return IterableUtils.toList(competitionRepository.findAllByName(name));
    }

    public List<Competition> getAllCompetitionsByMaxTeamSize(Integer maxTeamSize)
    {
        return IterableUtils.toList(competitionRepository.findAllByMaxTeamSize(maxTeamSize));
    }
}
