package com.webapp.eventportal.repository;

import com.webapp.eventportal.model.Institution;
import com.webapp.eventportal.model.Team;
import com.webapp.eventportal.model.User;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface TeamRepository extends CrudRepository<Team,Long> {

    Iterable<Team> findAllByName(String name);

    Iterable<Team> findAllByInstitution_Id(Long id);

    Iterable<Team> findAllByTeamLead_Id(Long id);

    Iterable<Team> findAllByInstitution(Institution institution);

    Iterable<Team> findAllByTeamLead(User teamLead);

    @Transactional
    void deleteById(Long id);
}
