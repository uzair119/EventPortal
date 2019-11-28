package com.webapp.eventportal.service;

import com.webapp.eventportal.model.User;
import com.webapp.eventportal.repository.UserRepository;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;



import javax.swing.text.html.Option;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service

public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    //for spring security
    @Override
    public org.springframework.security.core.userdetails.User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameAndActive(username, true);
        System.out.println("user");
        System.out.println(user.getUsername()+ " " +  " " + user.getPassword());
        Set<GrantedAuthority> grantedAuthorities = new HashSet();
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().getName()));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }

    public User save(User user)
    {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<User> getAllUsers()
    {
        return IterableUtils.toList(userRepository.findAllByActive(true));
    }

    public void deleteUser(String username)
    {
        userRepository.deleteUserByUsername(username);
    }

    public boolean existsByUsername(String username)
    {
        return userRepository.existsByUsernameAndActive(username,true);
    }

    public User getUserByUsername(String username)
    {
        return userRepository.findByUsernameAndActive(username,true);
    }
}
