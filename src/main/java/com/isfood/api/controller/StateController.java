package com.isfood.api.controller;

import java.util.List;

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

import com.isfood.api.assembler.StateDTOAssembler;
import com.isfood.api.assembler.StateDTODisassembler;
import com.isfood.api.model.StateDTO;
import com.isfood.domain.entity.State;
import com.isfood.domain.exception.EntityInUseException;
import com.isfood.domain.repository.StateRepository;
import com.isfood.domain.service.RegisterStateService;

@RestController
@RequestMapping("/state")
public class StateController {
	
	@Autowired
	private StateDTOAssembler stateDTOAssembler;
	
	@Autowired
	private StateDTODisassembler stateDTODisassembler;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private RegisterStateService registerStateService;

    @GetMapping
    public List<StateDTO> list(){
        return stateDTOAssembler.toCollectionDTO(stateRepository.findAll()) ;
    }


    @GetMapping("/{stateId}")
    public StateDTO find(@PathVariable Integer stateId){

        return stateDTOAssembler.toDTO(registerStateService.findOrFail(stateId)); 
    }
    
//    @GetMapping("/{stateId}")
//    public ResponseEntity<State> find(@PathVariable Integer stateId){
//    	
//    	Optional<State> state = stateRepository.findById(stateId);
//    	if (state != null)
//    		return ResponseEntity.ok(state.get());
//    	
//    	return ResponseEntity.notFound().build();
//    }

    @PostMapping
    public StateDTO save(@RequestBody @Valid StateDTO stateDTO){
    	State state = stateDTODisassembler.toDomainObject(stateDTO);
    	
        return stateDTOAssembler.toDTO(registerStateService.save(state)) ;
    }

    @PutMapping("/{stateId}")
    public StateDTO update (@PathVariable Integer stateId, @RequestBody @Valid StateDTO stateDTO){
        State stateActual = registerStateService.findOrFail(stateId);

        stateDTO.setId(stateId);
        
        stateDTODisassembler.copyToDomainObject(stateDTO, stateActual);
    	
       return stateDTOAssembler.toDTO(registerStateService.udpate(stateId, stateActual)) ;
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

