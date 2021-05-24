package com.isfood.domain.service;

import com.isfood.domain.entity.Permission;
import com.isfood.domain.exception.EntityInUseException;
import com.isfood.domain.exception.PermissionNotFoundException;
import com.isfood.domain.repository.PermissionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class RegisterPermissionService {

    public static final String MSG_Permission_IN_USE =
            "Permission with code: %d cannot be removed, because it is in use.";

    @Autowired
    PermissionRepository permissionRepository;

    public Permission save(Permission permission){
        return permissionRepository.save(permission);
    }
    
    public List<Permission> findAll(){
        return permissionRepository.findAll();
    }    

    public Permission udpate(Integer id, Permission permission){
    	Permission permissionBefore = findOrFail(id);
        BeanUtils.copyProperties(permission, permissionBefore, "id");

        return permissionRepository.save(permissionBefore);
    }

    @Transactional
    public void delete(Integer id){
        try {
        	permissionRepository.deleteById(id);
        	permissionRepository.flush();
        }catch (EmptyResultDataAccessException e) {
            throw new PermissionNotFoundException(id);

        }catch (DataIntegrityViolationException e){
            throw new EntityInUseException(
                    String.format(MSG_Permission_IN_USE, id));
        }
    }

    public Permission findOrFail(Integer id){
        return permissionRepository.findById(id)
                .orElseThrow(() -> new PermissionNotFoundException(id));
    }
}

