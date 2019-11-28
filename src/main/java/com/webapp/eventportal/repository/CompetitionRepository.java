package com.webapp.eventportal.repository;

import com.webapp.eventportal.model.Competition;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CompetitionRepository extends CrudRepository<Competition,Long> {
    List<Competition> findAllByName(String name);
    List<Competition> findAllByMaxTeamSize(Integer maxTeamSize);
}
