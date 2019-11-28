package com.webapp.eventportal.repository;

import com.webapp.eventportal.model.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findRoleByNameAndActive(String name, boolean active);
}
