package com.grapeup;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mcabaj
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @RequestMapping(value = "/{name}")
    public User getUsers(@PathVariable String name) {
        return new User(name, "Cabaj");
    }
}
