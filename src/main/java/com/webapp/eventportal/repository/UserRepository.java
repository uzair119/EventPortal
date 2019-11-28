package com.webapp.eventportal.repository;

import com.webapp.eventportal.model.User;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface UserRepository extends CrudRepository<User,String> {

    User findByUsernameAndActive(String username, boolean active);

    boolean existsByUsernameAndActive(String username, boolean active);

    List<User> findAllByActive(boolean active);

    @Transactional
    void deleteUserByUsername(String username);
}
