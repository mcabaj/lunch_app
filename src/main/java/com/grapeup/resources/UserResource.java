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

    @RequestMapping(method = RequestMethod.POST, 
            consumes="application/json",
            produces="application/json")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User created = userRepository.save(user);
        return new ResponseEntity<User>(created, HttpStatus.CREATED);
    }

    @RequestMapping
    public List<User> getUsers() {
        return userRepository.findAll();
    }
    
    @RequestMapping("/{userId}")
    public User getUser(@PathVariable String userId) {
        return userRepository.findOne(userId);
    }
    
    @RequestMapping("/{userId}/delete")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        userRepository.delete(userId);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
