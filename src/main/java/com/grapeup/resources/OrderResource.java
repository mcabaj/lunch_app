package com.grapeup.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.grapeup.domain.Order;
import com.grapeup.domain.Venue;
import com.grapeup.repositories.OrderRepository;
import com.grapeup.repositories.VenueRepository;

/**
 * @author mcabaj
 */
@RestController
@RequestMapping("/venues/{venueId}/orders")
public class OrderResource {

    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private VenueRepository venueRepository;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Order> addOrder(@PathVariable String venueId, @RequestBody Order order) {
        Venue venue = venueRepository.findOne(venueId);
        order.setVenue(venue);
    	orderRepository.save(order);
        return new ResponseEntity<Order>(HttpStatus.CREATED);
    }
    
    @RequestMapping("/{orderId}")
    public Order getOrder(@PathVariable String venueId, @PathVariable String orderId) {
        Order findOne = orderRepository.findOne(orderId);
        return findOne;
    }
    
    @RequestMapping
    public Order getOrders(@PathVariable String venueId) {
        return orderRepository.findByVenueId(venueId);
    }
    
}
