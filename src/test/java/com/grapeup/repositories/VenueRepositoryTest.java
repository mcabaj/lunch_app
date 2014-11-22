package com.grapeup.repositories;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

import java.util.List;

import javax.naming.spi.DirStateFactory.Result;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.grapeup.configs.WebMvcConfig;
import com.grapeup.domain.Venue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=WebMvcConfig.class)
@WebAppConfiguration
public class VenueRepositoryTest {
	
	@Autowired
	VenueRepository tested;

	@Before
	public void setUp() throws Exception {
		Venue r = new Venue();
		r.setName("TestRestaurant");
		r.setLink("http://localhost");
		r.setPhone("123123123");
		tested.save(r);
	}

	@After
	public void tearDown() throws Exception {
		tested.deleteAll();
	}

	@Test
	public void testFindAll() {
		List<Venue> result = tested.findAll();
		assertThat(result, hasSize(1));
		
	}

	@Test
	public void testFindByName() {
		List<Venue> result = tested.findByName("TestRestaurant");
		assertThat(result.get(0).getName(), equalTo("TestRestaurant"));
	}

}
