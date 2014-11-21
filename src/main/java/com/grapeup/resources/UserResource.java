package com.grapeup.resources;

import com.grapeup.domain.User;
import com.grapeup.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Void> addUser(@RequestBody User user) {
        userRepository.save(user);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @RequestMapping
    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
