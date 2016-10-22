package com.grapeup.resources;

import com.grapeup.domain.Group;
import com.grapeup.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
