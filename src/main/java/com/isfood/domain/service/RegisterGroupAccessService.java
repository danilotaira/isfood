package com.isfood.domain.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.isfood.domain.entity.GroupAccess;
import com.isfood.domain.exception.ControllerException;
import com.isfood.domain.exception.EntityInUseException;
import com.isfood.domain.exception.GroupAccessNotFoundException;
import com.isfood.domain.repository.GroupAccessRepository;

@Service
public class RegisterGroupAccessService {

    public static final String MSG_GROUP_ACCESS_IN_USE =
            "Group Access with code: %d cannot be removed, because it is in use.";

    @Autowired
    private GroupAccessRepository groupAccessRepository;
    
    
    public GroupAccess save(GroupAccess groupAccess){
        return groupAccessRepository.save(groupAccess);
    }

    public GroupAccess udpate(Integer id, GroupAccess groupAccess) throws ControllerException{
    	GroupAccess groupAccessBefore = this.findOrFail(id);
    	
        BeanUtils.copyProperties(groupAccess, groupAccessBefore, "id");

        return groupAccessRepository.save(groupAccessBefore);
    }

    public void delete(Integer id) throws ControllerException{
        try {
        	groupAccessRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e) {
            throw new GroupAccessNotFoundException(id);

        }catch (DataIntegrityViolationException e){
            throw new EntityInUseException(
                    String.format(MSG_GROUP_ACCESS_IN_USE, id));
        }
    }
    
    public GroupAccess findOrFail(Integer groupAccessId){
        return groupAccessRepository.findById(groupAccessId)
                .orElseThrow(() -> new GroupAccessNotFoundException(groupAccessId));
    }

	public List<GroupAccess> findAll() {
		return groupAccessRepository.findAll();
	}
}

