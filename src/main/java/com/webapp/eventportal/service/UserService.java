package com.webapp.eventportal.service;

import com.webapp.eventportal.model.User;
import com.webapp.eventportal.repository.UserRepository;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service

public class UserService {

    @Autowired
    UserRepository userRepository;

    public User save(User user)
    {
        return userRepository.save(user);
    }

    public List<User> getAllUsers()
    {
        return IterableUtils.toList(userRepository.findAll());
    }

    public void deleteUser(String username)
    {
        userRepository.deleteUserByUsername(username);
    }

    public boolean existsByUsername(String username)
    {
        return userRepository.existsByUsername(username);
    }

    public User getUserbyUsername(String username)
    {
        Optional<User> user = userRepository.findById(username);
        return user.orElse(null);
    }
}
