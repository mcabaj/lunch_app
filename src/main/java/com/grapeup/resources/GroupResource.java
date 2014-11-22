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
package com.grapeup.resources;

import com.grapeup.domain.Group;
import com.grapeup.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author mcabaj
 */
@RestController
@RequestMapping("/groups")
public class GroupResource {

    @Autowired
    private GroupRepository groupRepository;

    @RequestMapping
    public List<Group> getGroups() {
        return groupRepository.findAll();
    }
}
