package com.isfood.api.controller;

import com.isfood.api.mapper.PermissionMapper;
import com.isfood.api.model.PermissionDTO;
import com.isfood.domain.entity.Permission;
import com.isfood.domain.exception.ControllerException;
import com.isfood.domain.exception.EntityInUseException;
import com.isfood.domain.exception.EntityNotFoundException;
import com.isfood.domain.exception.StateNotFoundException;
import com.isfood.domain.service.RegisterPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/permission")
public class PermissionController {
	
	@Autowired
	private PermissionMapper permissionMapper;
	
    @Autowired
    private RegisterPermissionService registerPermissionService;

    @GetMapping
    public List<PermissionDTO> list(){
        return permissionMapper.toCollectionDTO(registerPermissionService.findAll()) ;
    }


    @GetMapping("/{permissionID}")
    public PermissionDTO find(@PathVariable Integer permissionID){

        return permissionMapper.toDTO(registerPermissionService.findOrFail(permissionID)) ;
    }

    @PostMapping
    public PermissionDTO save(@RequestBody @Valid PermissionDTO permissionDTO){
        try{
        	Permission permission = permissionMapper.toDomainObject(permissionDTO);
            return permissionMapper.toDTO(registerPermissionService.save(permission));
        }catch (StateNotFoundException e){
            throw new ControllerException(e.getMessage(), e);
        }
    }

    @PutMapping("/{permissionID}")
    public PermissionDTO update (@PathVariable Integer permissionID, @Valid @RequestBody PermissionDTO permissionDTO){
        try{
        	Permission permissionActual = registerPermissionService.findOrFail(permissionID);

        	permissionDTO.setId(permissionID);
            
        	permissionMapper.copyToDomainObject(permissionDTO, permissionActual);
//            BeanUtils.copyProperties(city, cityActual, "id");
            return permissionMapper.toDTO(registerPermissionService.save(permissionActual)) ;
        }catch (StateNotFoundException e){
            throw new ControllerException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{permissionID}")
    public ResponseEntity<?> delete(@PathVariable Integer permissionID) {
        try {
        	registerPermissionService.delete(permissionID);
            return ResponseEntity.noContent().build();
        } catch (EntityInUseException e) {
            throw e;
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

