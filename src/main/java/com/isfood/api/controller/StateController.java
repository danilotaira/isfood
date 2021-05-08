package com.isfood.api.controller;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
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

import com.isfood.domain.entity.State;
import com.isfood.domain.exception.EntityInUseException;
import com.isfood.domain.repository.StateRepository;
import com.isfood.domain.service.RegisterStateService;

@RestController
@RequestMapping("/state")
public class StateController {

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private RegisterStateService registerStateService;

    @GetMapping
    public List<State> list(){
        return stateRepository.findAll();
    }


    @GetMapping("/{stateId}")
    public ResponseEntity<State> find(@PathVariable Integer stateId){

        Optional<State> state = stateRepository.findById(stateId);
        if (state != null)
            return ResponseEntity.ok(state.get());

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public State save(@RequestBody @Valid State state){
        return registerStateService.save(state);
    }

    @PutMapping("/{stateId}")
    public State update (@PathVariable Integer stateId, @RequestBody @Valid State state){
           return registerStateService.udpate(stateId, state);
    }

    @DeleteMapping("/{stateId}")
    public ResponseEntity<?> delete(@PathVariable Integer stateId) {
        try {
            registerStateService.delete(stateId);
            return ResponseEntity.noContent().build();
        } catch (EntityInUseException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

