package com.isfood.domain.service;

import com.isfood.domain.entity.GroupAccess;
import com.isfood.domain.entity.Permission;
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
import java.util.Optional;

@Service
public class RegisterUserAccessService {

    public static final String MSG_USER_ACCESS_IN_USE =
            "User Access with code: %d cannot be removed, because it is in use.";

    @Autowired
    private UserAccessRepository userAccessRepository;

    @Autowired
    private RegisterGroupAccessService registerGroupAccessService;
    
    @Transactional
    public UserAccess save(UserAccess userAccess){
        userAccessRepository.detach(userAccess);
        Optional<UserAccess> userEmailExistent = userAccessRepository.findByEmail(userAccess.getEmail());

        if (userEmailExistent.isPresent() && !userEmailExistent.get().equals(userAccess)){
            throw new ControllerException(
                    String.format("There is already a registered user with this email - %s", userAccess.getEmail()));
        }

//        if (userAccessRepository.existsByEmailAndIdNot(userAccess.getEmail(), userAccess.getId())) {
//            throw new ControllerException(
//                    String.format("There is already a registered user with this email - %s", userAccess.getEmail()));
//        }

        return userAccessRepository.save(userAccess);
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

    @Transactional
    public void removeGroup(Integer userAccessId, Integer groupAccessId) {
        UserAccess userAccess = findOrFail(userAccessId);
        GroupAccess groupAccess = registerGroupAccessService.findOrFail(groupAccessId);

        userAccess.removeGroupAcess(groupAccess);
    }

    @Transactional
    public void addGroup(Integer userAccessId, Integer groupAccessId) {
        UserAccess userAccess = findOrFail(userAccessId);
        GroupAccess groupAccess = registerGroupAccessService.findOrFail(groupAccessId);

        userAccess.addGroupAcess(groupAccess);
    }
    
    public UserAccess findOrFail(Integer groupAccessId){
        return userAccessRepository.findById(groupAccessId)
                .orElseThrow(() -> new UserAccessNotFoundException(groupAccessId));
    }

	public List<UserAccess> findAll() {
		return userAccessRepository.findAll();
	}
}

