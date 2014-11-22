package com.grapeup.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author mcabaj
 */
@Document(collection = "users")
public class User {

    @Id
    private String id;
    @Indexed(unique=true)
    private String username;
    private String password;

    public User() {
    }

	public String getId() {
		return id;
	}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(String id) {
        this.id = id;
    }


 
}
