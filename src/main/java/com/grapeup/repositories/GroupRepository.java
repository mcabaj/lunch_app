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

import com.grapeup.domain.Group;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author mcabaj
 */
@Repository
public interface GroupRepository extends CrudRepository<Group, String> {

    public List<Group> findAll();
}
