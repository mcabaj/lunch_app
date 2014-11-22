package com.grapeup.resources;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.grapeup.configs.MongoConfig;
import com.grapeup.configs.WebMvcConfig;
import com.grapeup.domain.Order;
import com.grapeup.domain.OrderEntry;
import com.grapeup.domain.Venue;
import com.grapeup.repositories.OrderRepository;
import com.grapeup.repositories.UserRepository;
import com.grapeup.repositories.VenueRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebMvcConfig.class, MongoConfig.class})
@WebAppConfiguration
public class OrderEntryResourceTest {
    
    @Inject
    protected WebApplicationContext webApplicationContext;
    
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VenueRepository venueRepository;
    
    @Autowired
    private OrderRepository orderRepository;
    
    
    private MockMvc mockMvc;
    
    private com.grapeup.domain.User testUser;

    private Venue testVenue;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        testUser = new com.grapeup.domain.User();
        testUser.setId("1");
        testUser.setUsername("testUser");
        userRepository.save(testUser);
        testVenue = new Venue();
        testVenue.setId("1");
        testVenue.setName("testVenue");
        venueRepository.save(testVenue);
    }

    @After
    public void tearDown() throws Exception {
        userRepository.deleteAll();
        venueRepository.deleteAll();
        orderRepository.deleteAll();
    }

    @Test
    public void addOrderEntryWhenThereAreNone() throws Exception {
        User user = new User("testUser","", AuthorityUtils.createAuthorityList("ROLE_PATRON"));
        TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken(user,null);
        SecurityContextHolder.getContext().setAuthentication(testingAuthenticationToken);
 
        
        String order = "{\"user\":{\"username\":\"user1\",\"password\":\"pass\"},\"food\":\"pizza\"}";
        //String order = "{}";
        mockMvc.perform(post("/venues/1/orderentries").principal(testingAuthenticationToken).contentType(MediaType.APPLICATION_JSON).content(order))
            .andExpect(status().isCreated());
        
        MvcResult ret = mockMvc.perform(get("/venues/1/orders"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].venue.name", is("testVenue")))
            .andExpect(jsonPath("$[0].orders.[0].user.username", is("user1")))
            .andExpect(jsonPath("$[0].caller.username", is("testUser")))
            .andReturn();
        
        System.out.println(ret.getResponse().getContentAsString());
    }
    
    @Test
    public void addOrderEntryWhenThereIsAlreadyOnePresent() throws Exception {
        Order order = new Order();
        com.grapeup.domain.User caller = new com.grapeup.domain.User();
        caller.setUsername("aontherUser");
        order.setVenue(testVenue);
        order.setCaller(caller);
        order.setOrdered(true);
        order.setDelivered(false);
        order.setOrders(new ArrayList<OrderEntry>());
        orderRepository.save(order);
        
        User user = new User("testUser","", AuthorityUtils.createAuthorityList("ROLE_PATRON"));
        TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken(user,null);
        SecurityContextHolder.getContext().setAuthentication(testingAuthenticationToken);
 
        
        String orderEntry = "{\"user\":{\"username\":\"user1\",\"password\":\"pass\"},\"food\":\"pizza\"}";
        //String order = "{}";
        mockMvc.perform(post("/venues/1/orderentries").principal(testingAuthenticationToken).contentType(MediaType.APPLICATION_JSON).content(orderEntry))
            .andExpect(status().isCreated());
        // TODO: orderentry is not added to order
        MvcResult ret = mockMvc.perform(get("/venues/1/orders"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].venue.name", is("testVenue")))
            .andExpect(jsonPath("$[0].caller.username", is("aontherUser")))
            //.andExpect(jsonPath("$[0].orders[0].user.username", is("user1")))
            //.andExpect(jsonPath("$[0].orders", is("user1")))
            .andReturn();
        
        System.out.println(ret.getResponse().getContentAsString());
    }

}
