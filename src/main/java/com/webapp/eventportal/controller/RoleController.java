package com.webapp.eventportal.controller;


import com.webapp.eventportal.model.Role;
import com.webapp.eventportal.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path="/auth/admin/role")
public class RoleController {
//
//    static final String ADMIN_PREFIX = "/auth/admin";
//    static final String AUTH_PREFIX = "/auth";
//    static final String CONTROLLER_PREFIX = "/role";
//
//    static final String ADMIN_ONLY = ADMIN_PREFIX+CONTROLLER_PREFIX;
//    static final String AUTH_ONLY = AUTH_PREFIX+CONTROLLER_PREFIX;

    @Autowired
    private RoleService roleService;

    @PostMapping(path ="/create")
    public Role create(@RequestBody Role role, HttpServletResponse httpServletResponse) throws IOException {
        if(roleService.exists(role.getId()))
            httpServletResponse.sendError(403);
        role.setCreatedDate(new Date());
        return roleService.save(role);
    }

    @GetMapping(path="")
    public List<Role> getAllRoles()
    {
        return roleService.getAllRoles();
    }

    @GetMapping(path="/{id}")
    public Role getRole(@PathVariable(name="id") Long id, HttpServletResponse httpServletResponse) throws IOException {
        Role role = roleService.getRoleByID(id);
        if(role == null)
            httpServletResponse.sendError(404);
        return role;
    }


}
