package com.isfood.domain.service;

import com.isfood.domain.entity.State;
import com.isfood.domain.exception.EntityInUseException;
import com.isfood.domain.exception.StateNotFoundException;
import com.isfood.domain.repository.StateRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;


@Service
public class RegisterStateService {

    public static final String MSG_STATE_IN_USE =
            "State with code: %d cannot be removed, because it is in use.";

    @Autowired
    StateRepository stateRepository;

    public State save(State state){
        return stateRepository.save(state);
    }

    public State udpate(Integer id, State state){
        State stateBefore = this.findOrFail(id);

        BeanUtils.copyProperties(state, stateBefore, "id");
        return stateRepository.save(stateBefore);
    }

    public void delete(Integer id){
        try {
            stateRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e) {
            throw new StateNotFoundException(id);

        }catch (DataIntegrityViolationException e){
            throw new EntityInUseException(
                    String.format(MSG_STATE_IN_USE, id));
        }
    }
    public State findOrFail(Integer stateId){
        return stateRepository.findById(stateId)
                .orElseThrow(() -> new StateNotFoundException(stateId));
    }
}

