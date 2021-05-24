package com.isfood.api.controller;

import com.isfood.api.mapper.GroupAccessMapper;
import com.isfood.api.mapper.PermissionMapper;
import com.isfood.api.model.GroupAccessDTO;
import com.isfood.api.model.PermissionDTO;
import com.isfood.api.model.input.GroupAccessInput;
import com.isfood.domain.entity.GroupAccess;
import com.isfood.domain.exception.ControllerException;
import com.isfood.domain.exception.EntityInUseException;
import com.isfood.domain.exception.EntityNotFoundException;
import com.isfood.domain.exception.StateNotFoundException;
import com.isfood.domain.service.RegisterGroupAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/groups/{groupId}/permissions")
public class GroupPermissionController {
	
	@Autowired
	private GroupAccessMapper groupAccessMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private RegisterGroupAccessService registerGroupAccessService;

    @GetMapping
    public List<PermissionDTO> list(@PathVariable Integer groupId){
        GroupAccess groupAccess = registerGroupAccessService.findOrFail(groupId);

        return permissionMapper.toCollectionDTO(groupAccess.getPermissions());
    }

    @DeleteMapping("/{permissionId}")
    public void disassociate(@PathVariable Integer permissionId, @PathVariable Integer groupId) {
        registerGroupAccessService.removePermission(groupId, permissionId);
    }

    @PutMapping("/{permissionId}")
    public void associate(@PathVariable Integer permissionId, @PathVariable Integer groupId) {
        registerGroupAccessService.addPermission(groupId, permissionId);
    }
}

