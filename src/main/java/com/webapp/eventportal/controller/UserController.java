package com.webapp.eventportal.controller;

import com.webapp.eventportal.model.User;
import com.webapp.eventportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path="/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(path="/create")
    public User createUser(@RequestBody User user, HttpServletResponse httpServletResponse) throws IOException {
        if(userService.exists(user.getUsername()))
            httpServletResponse.sendError(403);
        return userService.save(user);
    }

    @GetMapping(path="")
    public List<User> getAllUsers()
    {
        return userService.getAllUsers();
    }

    @GetMapping(path="/{username}")
    public User getUserByID(@PathVariable(name="username") String username, HttpServletResponse httpServletResponse) throws IOException {
        User user = userService.getUser(username);
        if(user == null)
            httpServletResponse.sendError(404);
        return user;
    }

    @DeleteMapping(path="/{username}")
    public void deleteUser(@PathVariable(name="username")String username)
    {
        userService.deleteUser(username);
    }
}
