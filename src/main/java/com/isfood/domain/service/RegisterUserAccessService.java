package com.isfood.domain.service;

import com.isfood.domain.entity.UserAccess;
import com.isfood.domain.exception.ControllerException;
import com.isfood.domain.exception.EntityInUseException;
import com.isfood.domain.exception.UserAccessNotFoundException;
import com.isfood.domain.repository.UserAccessRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class RegisterUserAccessService {

    public static final String MSG_USER_ACCESS_IN_USE =
            "User Access with code: %d cannot be removed, because it is in use.";

    @Autowired
    private UserAccessRepository userAccessRepository;
    
    
    public UserAccess save(UserAccess groupAccess){
        return userAccessRepository.save(groupAccess);
    }

    @Transactional
    public void changePassword(Integer userAccessId, String passwordActual, String passwordNew) {
        UserAccess userAccess = findOrFail(userAccessId);

        if (!userAccess.passwordMatchesWith(passwordActual)) {
            throw new ControllerException("The Password entered does not matches the user's password.");
        }

        userAccess.setPassword(passwordNew);
    }

    public UserAccess udpate(Integer id, UserAccess groupAccess) throws ControllerException{
    	UserAccess userAccessBefore = this.findOrFail(id);
    	
        BeanUtils.copyProperties(groupAccess, userAccessBefore, "id");

        return userAccessRepository.save(userAccessBefore);
    }

    public void delete(Integer id) throws ControllerException{
        try {
        	userAccessRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e) {
            throw new UserAccessNotFoundException(id);

        }catch (DataIntegrityViolationException e){
            throw new EntityInUseException(
                    String.format(MSG_USER_ACCESS_IN_USE, id));
        }
    }
    
    public UserAccess findOrFail(Integer groupAccessId){
        return userAccessRepository.findById(groupAccessId)
                .orElseThrow(() -> new UserAccessNotFoundException(groupAccessId));
    }

	public List<UserAccess> findAll() {
		return userAccessRepository.findAll();
	}
}

