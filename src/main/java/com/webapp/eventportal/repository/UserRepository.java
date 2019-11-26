package com.webapp.eventportal.repository;

import com.webapp.eventportal.model.User;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface UserRepository extends CrudRepository<User,String> {
    @Override
    User save(User user);
    User findByUsername(String username);
    boolean existsByUsername(String username);

    @Override
    boolean existsById(String s);

    @Transactional
    void deleteUserByUsername(String username);
}
