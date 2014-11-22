package com.grapeup.resources;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.grapeup.configs.WebMvcConfig;
import com.grapeup.domain.Order;
import com.grapeup.domain.OrderEntry;
import com.grapeup.domain.User;
import com.grapeup.domain.Venue;
import com.grapeup.repositories.OrderRepository;
import com.grapeup.repositories.VenueRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WebMvcConfig.class)
@WebAppConfiguration
public class OrderResourceTest {

    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private VenueRepository venueRepository;
    

    @Inject
    protected WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        Order order = new Order();
        order.setId("1");
        User caller = new User();
        caller.setUsername("user");
        caller.setPassword("pass");
        caller.setId("1");
        order.setCaller(caller);
        order.setDate(Calendar.getInstance().getTime());
        order.setDelivered(false);
        order.setEta(1010);
        
        Venue venue = new Venue();
        venue.setName("TestVenue");
        venue.setPhone("123-123-123");
        venue.setLink("http://test-venue.com");
        venue.setId("1");
        venueRepository.save(venue);
        order.setVenue(venue);
        List<OrderEntry> orders = new ArrayList<>();
        OrderEntry entry = new OrderEntry();
        entry.setFood("pizza");
        User user = new User();
        user.setUsername("user1");
        entry.setUser(user);
        orders.add(entry );
        entry = new OrderEntry();
        entry.setFood("sandwich");
        user = new User();
        user.setUsername("user2");
        entry.setUser(user);
        orders.add(entry );
        order.setOrders(orders);
        orderRepository.save(order);
        
    }
    

    @After
    public void tearDown() throws Exception {
        orderRepository.deleteAll();
    }

 

    @Test
    public void getOrders() throws Exception {
       MvcResult res = mockMvc.perform(get("/venues"))
            .andExpect(status().isOk())
            .andReturn();
        
        System.out.println(res.getResponse().getContentAsString());
        res = mockMvc.perform(get("/venues/1/orders"))
            .andExpect(status().isOk())
            .andReturn();
            
        System.out.println(res.getResponse().getContentAsString());
    }

    

}
