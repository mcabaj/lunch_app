package com.grapeup.resources;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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
import com.grapeup.domain.OrderEntry;
import com.grapeup.domain.User;
import com.grapeup.domain.Venue;
import com.grapeup.repositories.OrderEntryRepository;
import com.grapeup.repositories.OrderRepository;
import com.grapeup.repositories.UserRepository;
import com.grapeup.repositories.VenueRepository;

/**
 * @author mcabaj
 */
@RestController
@RequestMapping("/venues/{venueId}/orderentries")
public class OrderEntryResource {

    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private OrderEntryRepository orderEntryRepository;
    
    @Autowired
    private VenueRepository venueRepository;
    
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> addOrderEntry(Principal principal, @PathVariable String venueId, @RequestBody OrderEntry orderEntry) {
        List<Order> orders = orderRepository.findByVenueId(venueId);
        for (Order order : orders) {
            if (order.isOrdered() && !order.isDelivered()) {
                order.getOrders().add(orderEntry);
                orderEntryRepository.save(orderEntry);
                return new ResponseEntity<Void>(HttpStatus.CREATED);
            }
        }
        String username = principal.getName();
        List<User> user = userRepository.findByUsername(username);
        Venue venue = venueRepository.findOne(venueId);
        if (user != null && user.size() == 1 && venue != null) {
            orderEntryRepository.save(orderEntry);
            Order order = crateOrder(user.get(0), venue, orderEntry);
            orderRepository.save(order);    
            return new ResponseEntity<Void>(HttpStatus.CREATED); 
        } else {
            return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);    
        }
    }

    private Order crateOrder(User user, Venue venue, OrderEntry orderEntry) {
        
        Order order = new Order();
        order.setDate(Calendar.getInstance().getTime());
        order.setVenue(venue);
        order.setCaller(user);
        List<OrderEntry> orderEntries = new ArrayList<>();
        orderEntries.add(orderEntry);
        order.setOrders(orderEntries);
        orderEntryRepository.save(orderEntries);   
        return order;
    }
    
    @RequestMapping("/{orderId}")
    public List<OrderEntry> getOrder(@PathVariable String venueId, @PathVariable String orderId) {
        Order order = orderRepository.findOne(orderId);
        if ( order != null) {
            return order.getOrders();
        } else {
            return Collections.emptyList();
        }
    }
    
}
