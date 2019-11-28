package com.webapp.eventportal.service;


import com.webapp.eventportal.model.Role;
import com.webapp.eventportal.repository.RoleRepository;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public Role save(Role role)
    {
        return roleRepository.save(role);
    }

    public Role getRoleByID(Long id)
    {
        Optional<Role> role = roleRepository.findById(id);
        return role.orElse(null);
    }

    public Role getRoleByName(String name)
    {
        return roleRepository.findRoleByNameAndActive(name,true);
    }

    public List<Role> getAllRoles()
    {
        return IterableUtils.toList(roleRepository.findAll());
    }

    public boolean exists(Long id)
    {
        return roleRepository.existsById(id);
    }
}
