package com.webapp.eventportal.repository;


import com.webapp.eventportal.model.Institution;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "institution", path = "institution")
public interface InstitutionRepository extends CrudRepository<Institution,Long> {
    List<Institution> findByName(@Param("name") String name);
}
