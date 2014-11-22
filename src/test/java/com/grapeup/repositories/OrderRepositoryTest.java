package com.grapeup.repositories;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.google.common.collect.Lists;
import com.grapeup.configs.WebMvcConfig;
import com.grapeup.domain.Order;
import com.grapeup.domain.OrderEntry;
import com.grapeup.domain.Venue;
import com.grapeup.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=WebMvcConfig.class)
@WebAppConfiguration
public class OrderRepositoryTest {

	@Autowired
	OrderRepository tested;
	
	@Autowired
	OrderEntryRepository orderEntryRepository;
	
	@Autowired
	UserRepository userRepository;
	
	
	@Before
	public void setUp() throws Exception {
//		User user = new User();
//		user.setName("test");
//		userRepository.save(user);
		
		Order order = new Order();
		User caller = new User();
		caller.setUsername("test");
		order.setCaller(caller);
		order.setDate(Calendar.getInstance().getTime());
		order.setDelivered(false);
		Venue restaurant = new Venue();
		restaurant.setName("Pinokio");
		order.setVenue(restaurant);
		List<OrderEntry> orders = Lists.newArrayList();
		OrderEntry entry = new OrderEntry();
		entry.setUser(caller);
		entry.setFood("kurczak");
		orders.add(entry);
		
		order.setOrders(orders);
		tested.save(order);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		User caller = new User();
		caller.setUsername("test");
		List<Order> result = tested.findByCaller(caller);
		assertThat(result.get(0).getOrders(), hasSize(1));
	}

}
