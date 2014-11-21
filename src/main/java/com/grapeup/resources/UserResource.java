package com.grapeup.resources;

import com.grapeup.domain.User;
import com.grapeup.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author mcabaj
 */
@RestController
@RequestMapping("/users")
public class UserResource {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> addUser(User user) {
        userRepository.save(user);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @RequestMapping
    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
