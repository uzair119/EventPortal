package com.webapp.eventportal.controller;

import com.webapp.eventportal.model.User;
import com.webapp.eventportal.service.RoleService;
import com.webapp.eventportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
//@RequestMapping(path="/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    static final String ADMIN_PREFIX = "/auth/admin";
    static final String AUTH_PREFIX = "/auth";
    static final String CONTROLLER_PREFIX = "/user";

    static final String ADMIN_ONLY = ADMIN_PREFIX+CONTROLLER_PREFIX;
    static final String AUTH_ONLY = AUTH_PREFIX+CONTROLLER_PREFIX;



    //for admin only
    @PostMapping(path=ADMIN_ONLY+"/create")
    public User createUser(@RequestBody User user, HttpServletResponse httpServletResponse) throws IOException {
        if(userService.existsByUsername(user.getUsername()))
            httpServletResponse.sendError(403);
//        user.setCreatedDate(new Date());
        return userService.save(user);
    }

    @PostMapping(path=CONTROLLER_PREFIX+"/register")
    public User registerUser(@RequestBody User user, HttpServletResponse httpServletResponse) throws IOException {
        if(userService.existsByUsername(user.getUsername()))
            httpServletResponse.sendError(403);
//        user.setCreatedDate(new Date());
        user.setRole(roleService.getRoleByName("USER"));
        return userService.save(user);
    }

    @GetMapping(path=ADMIN_ONLY)
    public List<User> getAllUsers()
    {
        return userService.getAllUsers();
    }

    @GetMapping(path=ADMIN_ONLY+"/{username}")
    public User getUserByID(@PathVariable(name="username") String username, HttpServletResponse httpServletResponse) throws IOException {
        User user = userService.getUserByUsername(username);
        if(user == null)
            httpServletResponse.sendError(404);
        return user;
    }

    @DeleteMapping(path=ADMIN_ONLY+"/{username}")
    public void deleteUser(@PathVariable(name="username")String username)
    {
        userService.deleteUser(username);
    }
}
