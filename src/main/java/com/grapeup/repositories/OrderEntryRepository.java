package com.grapeup.repositories;

import com.grapeup.domain.OrderEntry;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderEntryRepository extends CrudRepository<OrderEntry, String> {

    List<OrderEntry> findAll();
    
}
