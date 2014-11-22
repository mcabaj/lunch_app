package com.grapeup.resources;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.inject.Inject;

import com.grapeup.configs.MongoConfig;
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

import com.grapeup.configs.WebMvcConfig;
import com.grapeup.domain.User;
import com.grapeup.repositories.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebMvcConfig.class, MongoConfig.class})
@WebAppConfiguration
public class UserResourceTest {
    
    @Inject
    protected WebApplicationContext webApplicationContext;
    
    private MockMvc mockMvc;
    
    @Inject
    private UserRepository userRepo;

    private User testUser;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        testUser = new User();
        testUser.setUsername("testuser");
        testUser.setPassword("password");
        testUser.setId("1");
        userRepo.save(testUser);
    }

    @After
    public void tearDown() throws Exception {
        userRepo.deleteAll();
    }

    @Test
    public void listAll() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username", is("testuser")))
                .andExpect(jsonPath("$[0].password", is("password")));
                
    }
    
    @Test
    public void add() throws Exception {
        String user = "{"
                + "\"username\":\"test\","
                + "\"password\":\"pass\""
                + "}";
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(user))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username", is("test")))
                .andExpect(jsonPath("$.password", is("pass")));
                
    }

    

    @Test
    public void getUser() throws Exception {
        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("testuser")))
                .andExpect(jsonPath("$.password", is("password")));
        
    }
    
    @Test
    public void delete() throws Exception {
        mockMvc.perform(get("/users/1/delete"))
                .andExpect(status().isOk());
        
    }

}
