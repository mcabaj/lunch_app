package com.grapeup.resources;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import com.grapeup.configs.MongoConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
@ContextConfiguration(classes = {WebMvcConfig.class, MongoConfig.class})
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
        
        Venue venue = createVenue("TestVenue", "123-123-123", "http://test-venue.com", "1");
        venueRepository.save(venue);
        User caller = craeteUser("user", "pass", "1");
        User user1= craeteUser("user1", "pass", "2");
        User user2= craeteUser("user2", "pass", "3");
        
        
        Order order = new Order();
        order.setId("1");
        order.setCaller(caller);
        order.setDate(Calendar.getInstance().getTime());
        order.setDelivered(false);
        order.setEta(1010);
        order.setVenue(venue);
        List<OrderEntry> orders = new ArrayList<>();
        OrderEntry entry = new OrderEntry();
        entry.setFood("pizza");
        entry.setUser(user1);
        entry.setId("1");
        orders.add(entry );
        entry = new OrderEntry();
        entry.setFood("sandwich");
        entry.setUser(user2);
        entry.setId("2");
        orders.add(entry );
        order.setOrders(orders);
        orderRepository.save(order);
        
    }


   
    

    @After
    public void tearDown() throws Exception {
        orderRepository.deleteAll();
        venueRepository.deleteAll();
    }
    
    @Test
    public void addOrder() throws Exception {
        String order = "{}";
        mockMvc.perform(post("/venues/1/orders").contentType(MediaType.APPLICATION_JSON).content(order))
            .andExpect(status().isCreated())
            .andReturn();
    }

    @Test
    public void getOrders() throws Exception {
        mockMvc.perform(get("/venues/1/orders"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].delivered", is(false)))
            .andExpect(jsonPath("$[0].orders.[0]food", is("pizza")))
            .andExpect(jsonPath("$[0].orders.[1]food", is("sandwich")));
    }
    
    @Test
    public void getOrder() throws Exception {
        mockMvc.perform(get("/venues/1/orders"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].delivered", is(false)))
            .andExpect(jsonPath("$[0].orders.[0]food", is("pizza")))
            .andExpect(jsonPath("$[0].orders.[1]food", is("sandwich")));
    }
    
    private User craeteUser(String username, String password, String id) {
        User caller = new User();
        caller.setUsername(username);
        caller.setPassword(password);
        caller.setId(id);
        return caller;
    }
    
    private User craeteUser(String username, String password) {
        User caller = new User();
        caller.setUsername(username);
        caller.setPassword(password);
        return caller;
    }


    private Venue createVenue(String name, String phone, String link, String id) {
        Venue venue = new Venue();
        venue.setName(name);
        venue.setPhone(phone);
        venue.setLink(link);
        venue.setId(id);
        return venue;
    }

    

}
