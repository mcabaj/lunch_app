package com.grapeup.resources;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.grapeup.configs.MongoConfig;
import com.grapeup.configs.WebMvcConfig;
import com.grapeup.domain.Venue;
import com.grapeup.repositories.UserRepository;
import com.grapeup.repositories.VenueRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={WebMvcConfig.class, MongoConfig.class})
@WebAppConfiguration
public class VenueResourceTest {
    
    @Inject
    protected WebApplicationContext webApplicationContext;
    
    private MockMvc mockMvc;
    
    @Inject
    private VenueRepository venueRepo;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        Venue venue = new Venue();
        venue.setDescription("testDescription");
        venue.setLink("http://myvenue.com");
        venue.setPhone("123-456-789");
        venue.setName("Pinokio");
        venue.setId("1");
        venueRepo.save(venue);
    }
    

    @After
    public void tearDown() throws Exception {
        venueRepo.deleteAll();
    }

   
    @Test
    public void testGetVenues() throws Exception {
        MvcResult ret = mockMvc.perform(get("/venues")).andReturn();
        System.out.println(ret.getResponse().getContentAsString());
        mockMvc.perform(get("/venues"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].name", is("Pinokio")))
            .andExpect(jsonPath("$[0].phone", is("123-456-789")))
            .andExpect(jsonPath("$[0].description", is("testDescription")))
            .andExpect(jsonPath("$[0].link", is("http://myvenue.com")));
    }
    
    @Test
    public void testGetVenue() throws Exception {
        mockMvc.perform(get("/venues/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", is("Pinokio")))
            .andExpect(jsonPath("$.phone", is("123-456-789")))
            .andExpect(jsonPath("$.description", is("testDescription")))
            .andExpect(jsonPath("$.link", is("http://myvenue.com")));
    }
    
    @Test
    public void addVenue() throws Exception {
        String venue = "{"
                + "\"name\":\"66\","
                + "\"phone\":\"123-123-123\","
                + "\"description\":\"testDescription\","
                + "\"link\":\"http://myvenue.com\""
                + "}";
        mockMvc.perform(post("/venues").contentType(MediaType.APPLICATION_JSON).content(venue))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name", is("66")))
            .andExpect(jsonPath("$.phone", is("123-123-123")))
            .andExpect(jsonPath("$.description", is("testDescription")))
            .andExpect(jsonPath("$.link", is("http://myvenue.com")));
        
        MvcResult ret = mockMvc.perform(get("/venues")).andReturn();
        System.out.println(ret.getResponse().getContentAsString());
    }

}
