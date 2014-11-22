/*
 * Avaya Inc. – Proprietary (Restricted)
 * Solely for authorized persons having a need to know
 * pursuant to Company instructions.
 *
 * Copyright © 2006-2014 Avaya Inc. All rights reserved.
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF Avaya Inc.
 * The copyright notice above does not evidence any actual
 * or intended publication of such source code.
 */
package com.grapeup.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author mcabaj
 */
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
