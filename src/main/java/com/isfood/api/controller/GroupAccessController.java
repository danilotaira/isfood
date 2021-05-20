package com.isfood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isfood.api.mapper.GroupAccessMapper;
import com.isfood.api.model.GroupAccessDTO;
import com.isfood.api.model.input.GroupAccessInput;
import com.isfood.domain.entity.GroupAccess;
import com.isfood.domain.exception.ControllerException;
import com.isfood.domain.exception.EntityInUseException;
import com.isfood.domain.exception.EntityNotFoundException;
import com.isfood.domain.exception.StateNotFoundException;
import com.isfood.domain.service.RegisterGroupAccessService;

@RestController
@RequestMapping("/groups")
public class GroupAccessController {
	
	@Autowired
	private GroupAccessMapper groupAccessMapper;
	
    @Autowired
    private RegisterGroupAccessService registerGroupAccessService;

    @GetMapping
    public List<GroupAccessDTO> list(){
        return groupAccessMapper.toCollectionDTO(registerGroupAccessService.findAll()) ;
    }

    @GetMapping("/{groupAccessId}")
    public GroupAccessDTO find(@PathVariable Integer groupAccessId) {

        return groupAccessMapper.toDTO(registerGroupAccessService.findOrFail(groupAccessId)) ;
    }

    @PostMapping
    public GroupAccessDTO save(@RequestBody @Valid GroupAccessInput groupAccessInput){
        try{
        	GroupAccess groupAccess = groupAccessMapper.toDomainObject(groupAccessInput);
            return groupAccessMapper.toDTO(registerGroupAccessService.save(groupAccess));
        }catch (StateNotFoundException e){
            throw new ControllerException(e.getMessage(), e);
        }
    }

    @PutMapping("/{groupAccessId}")
    public GroupAccessDTO update (@PathVariable Integer groupAccessId, @Valid @RequestBody GroupAccessDTO groupAccessDTO){
        try{
            GroupAccess groupAccessActual = registerGroupAccessService.findOrFail(groupAccessId);

            groupAccessDTO.setId(groupAccessId);
            
            groupAccessMapper.copyToDomainObject(groupAccessDTO, groupAccessActual);
//            BeanUtils.copyProperties(groupAccess, groupAccessActual, "id");
            return groupAccessMapper.toDTO(registerGroupAccessService.save(groupAccessActual)) ;
        }catch (Exception e){
            throw new ControllerException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{groupAccessId}")
    public ResponseEntity<?> delete(@PathVariable Integer groupAccessId) {
        try {
            registerGroupAccessService.delete(groupAccessId);
            return ResponseEntity.noContent().build();
        } catch (EntityInUseException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

