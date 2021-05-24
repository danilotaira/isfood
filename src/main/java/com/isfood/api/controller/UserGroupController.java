package com.isfood.api.controller;

import com.isfood.api.mapper.GroupAccessMapper;
import com.isfood.api.mapper.UserAccessMapper;
import com.isfood.api.model.GroupAccessDTO;
import com.isfood.domain.entity.UserAccess;
import com.isfood.domain.service.RegisterUserAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/groups")
public class UserGroupController {
	
	@Autowired
	private UserAccessMapper userAccessMapper;

    @Autowired
    private GroupAccessMapper groupAccessMapper;

    @Autowired
    private RegisterUserAccessService registerUserAccessService;

    @GetMapping
    public List<GroupAccessDTO> list(@PathVariable Integer userId){
        UserAccess userAccess = registerUserAccessService.findOrFail(userId);

        return groupAccessMapper.toCollectionDTO(userAccess.getGroupAccesses());
    }

    @DeleteMapping("/{groupAccessId}")
    public void disassociate(@PathVariable Integer groupAccessId, @PathVariable Integer userId) {
        registerUserAccessService.removeGroup(userId, groupAccessId);
    }

    @PutMapping("/{groupAccessId}")
    public void associate(@PathVariable Integer groupAccessId, @PathVariable Integer userId) {
        registerUserAccessService.addGroup(userId, groupAccessId);
    }
}

