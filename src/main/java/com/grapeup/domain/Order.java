package com.grapeup.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "orders")
public class Order {

	@Id
	private String id;
	private Venue venue;
	private List<OrderEntry> orders;
	private User caller;
	private Date date;
	private Integer eta;
	private boolean ordered;
	private boolean delivered;
	
	
	public List<OrderEntry> getOrders() {
		return orders;
	}
	public void setOrders(List<OrderEntry> orders) {
		this.orders = orders;
	}
	public User getCaller() {
		return caller;
	}
	public void setCaller(User caller) {
		this.caller = caller;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Integer getEta() {
		return eta;
	}
	public void setEta(Integer eta) {
		this.eta = eta;
	}
	public boolean isDelivered() {
		return delivered;
	}
	public void setDelivered(boolean delivered) {
		this.delivered = delivered;
	}
	public String getId() {
		return id;
	}
    public Venue getVenue() {
        return venue;
    }
    public void setVenue(Venue venue) {
        this.venue = venue;
    }
    public void setId(String id) {
        this.id = id;
    }
    public boolean isOrdered() {
        return ordered;
    }
    public void setOrdered(boolean ordered) {
        this.ordered = ordered;
    }

	
}
