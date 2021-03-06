package com.grapeup.resources;

import com.grapeup.domain.Venue;
import com.grapeup.repositories.VenueRepository;
import com.grapeup.websocket.ChangeNotifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/venues")
public class VenueResource {

    @Autowired
    private VenueRepository venueRepository;
    @Autowired
    private ChangeNotifier changeNotifier;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Venue> addVenue(@RequestBody Venue venue) {
    	Venue created = venueRepository.save(venue);
        changeNotifier.notifyVenuesChanged();
        return new ResponseEntity<Venue>(created, HttpStatus.CREATED);
    }

    @RequestMapping
    public List<Venue> getVenues() {
        return venueRepository.findAll();
    }
    
    @RequestMapping("{venueId}")
    public Venue getVenue(@PathVariable String venueId) {
        return venueRepository.findOne(venueId);
    }
}
