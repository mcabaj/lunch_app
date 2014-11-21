package com.grapeup.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author mcabaj
 */
@Document(collection = "users")
public class User {

    @Id
    private String id;
    private String firstName;
    private String lastName;

    public User(String firstname, String lastname) {
        this.firstName = firstname;
        this.lastName = lastname;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
