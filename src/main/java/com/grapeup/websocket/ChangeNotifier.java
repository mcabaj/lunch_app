package com.grapeup.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChangeNotifier {

    @Autowired
    private Broadcaster broadcaster;

    public void notifyVenuesChanged() {
        broadcaster.broadcastMessage("venues_changed");
    }

    public void notifyOrdersChanged(String venueId) {
        broadcaster.broadcastMessage("meals_changed?venue_id="+venueId);
    }
}
