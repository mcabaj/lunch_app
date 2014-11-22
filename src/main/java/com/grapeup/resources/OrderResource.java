package com.grapeup.resources;

import java.util.List;

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
    public ResponseEntity<Void> addOrder(@RequestBody Order order) {
    	orderRepository.save(order);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

//    @RequestMapping
//    public List<Order> getOrders() {
//        return orderRepository.findAll();
//    }
    
    @RequestMapping("/{orderId}")
    public Order getOrder(@PathVariable String venueId, @PathVariable String orderId) {
        return orderRepository.findOne(orderId);
    }
    
    @RequestMapping
    public Order getOrders(@PathVariable String venueId) {
        //Venue venue = venueRepository.findOne(venueId); 
        //return orderRepository.findByVenue(venue);
        return orderRepository.findByVenueId(venueId);
    }
}
