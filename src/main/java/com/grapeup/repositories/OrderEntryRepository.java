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
package com.grapeup.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.grapeup.domain.OrderEntry;
import com.grapeup.domain.User;

/**
 * @author mcabaj
 */
@Repository
public interface OrderEntryRepository extends CrudRepository<OrderEntry, String> {

    public List<OrderEntry> findAll();
    public List<OrderEntry> findByUser(User user);
    
}
