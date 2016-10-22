package com.grapeup.repositories;

import com.grapeup.domain.Venue;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VenueRepository extends CrudRepository<Venue, String> {

    List<Venue> findAll();
    List<Venue> findByName(String name);
    
}
