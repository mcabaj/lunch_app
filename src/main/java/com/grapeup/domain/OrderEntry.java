package com.grapeup.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "orderentries")
public class OrderEntry {

	@Id
	private String id;
	private User user;
	private String food;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getFood() {
		return food;
	}
	public void setFood(String food) {
		this.food = food;
	}
	public String getId() {
		return id;
	}
    public void setId(String id) {
        this.id = id;
    }

	
}
