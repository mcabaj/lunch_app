package com.grapeup.repositories;

import com.grapeup.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

    List<User> findAll();

    List<User> findByUsername(String username);

    User findByUsernameAndPassword(String username, String password);

}
