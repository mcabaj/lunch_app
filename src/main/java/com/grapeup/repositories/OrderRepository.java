package com.grapeup.repositories;

import com.grapeup.domain.Order;
import com.grapeup.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, String> {

    List<Order> findAll();
    List<Order> findByCaller(User caller);
    List<Order> findByVenueId(String venueId);
    List<Order> findByVenueIdAndDeliveredAndOrdered(String venueId, boolean delivered, boolean ordered);
    
}
