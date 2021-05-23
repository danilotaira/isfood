package com.isfood.api.controller;

import com.isfood.api.mapper.UserAccessMapper;
import com.isfood.api.model.UserAccessDTO;
import com.isfood.api.model.input.UserAccessInput;
import com.isfood.api.model.input.UserAccessPasswordInput;
import com.isfood.api.model.input.UserAccessUpdateInput;
import com.isfood.domain.entity.UserAccess;
import com.isfood.domain.exception.ControllerException;
import com.isfood.domain.exception.EntityInUseException;
import com.isfood.domain.exception.EntityNotFoundException;
import com.isfood.domain.exception.StateNotFoundException;
import com.isfood.domain.service.RegisterUserAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserAccessController {
	
	@Autowired
	private UserAccessMapper userAccessMapper;
	
    @Autowired
    private RegisterUserAccessService registerUserAccessService;

    @GetMapping
    public List<UserAccessDTO> list(){
        return userAccessMapper.toCollectionDTO(registerUserAccessService.findAll()) ;
    }

    @GetMapping("/{userAccessId}")
    public UserAccessDTO find(@PathVariable Integer userAccessId) {

        return userAccessMapper.toDTO(registerUserAccessService.findOrFail(userAccessId)) ;
    }

    @PostMapping
    public UserAccessDTO save(@RequestBody @Valid UserAccessInput userAccessInput){
        try{
        	UserAccess userAccess = userAccessMapper.toDomainObject(userAccessInput);
            return userAccessMapper.toDTO(registerUserAccessService.save(userAccess));
        }catch (StateNotFoundException e){
            throw new ControllerException(e.getMessage(), e);
        }
    }

    @PutMapping("/{userAccessId}")
    public UserAccessDTO update (@PathVariable Integer userAccessId, @Valid @RequestBody UserAccessUpdateInput userAccessUpdateInput){
        try{
            UserAccess userAccessActual = registerUserAccessService.findOrFail(userAccessId);
            userAccessMapper.copyToDomainObject(userAccessUpdateInput, userAccessActual);
//            BeanUtils.copyProperties(userAccess, userAccessActual, "id");
            return userAccessMapper.toDTO(registerUserAccessService.save(userAccessActual)) ;
        }catch (Exception e){
            throw new ControllerException(e.getMessage(), e);
        }
    }

    @PutMapping("/{userAccessId}/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@PathVariable Integer userAccessId, @RequestBody @Valid UserAccessPasswordInput userAccessPasswordInput) {
        registerUserAccessService.changePassword(userAccessId, userAccessPasswordInput.getPasswordActual(), userAccessPasswordInput.getPasswordNew());
    }

    @DeleteMapping("/{userAccessId}")
    public ResponseEntity<?> delete(@PathVariable Integer userAccessId) {
        try {
            registerUserAccessService.delete(userAccessId);
            return ResponseEntity.noContent().build();
        } catch (EntityInUseException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

