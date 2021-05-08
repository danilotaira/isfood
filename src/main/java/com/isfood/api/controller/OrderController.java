package com.isfood.api.controller;

import com.isfood.domain.entity.OrderCustomer;
import com.isfood.domain.exception.EntityInUseException;
import com.isfood.domain.repository.OrderRepository;
import com.isfood.domain.service.RegisterOrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RegisterOrderService registerOrderService;

    @GetMapping
    public List<OrderCustomer> list(){
        return orderRepository.findAll();
    }
    
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderCustomer> find(@PathVariable Long orderId){

        Optional<OrderCustomer> kitchen = orderRepository.findById(orderId);
        if (kitchen.isPresent())
            return ResponseEntity.ok(kitchen.get());

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderCustomer add(@RequestBody OrderCustomer kitchen){
        return registerOrderService.save(kitchen);
    }

    @PutMapping("/{kitchenId}")
    public ResponseEntity<OrderCustomer> update (@PathVariable long kitchenId, @RequestBody OrderCustomer kitchen){
        Optional<OrderCustomer> kitchenActual = orderRepository.findById(kitchenId);

        if(kitchenActual.isPresent()){
            BeanUtils.copyProperties(kitchen, kitchenActual.get(), "id");
            OrderCustomer kitchenSaved = registerOrderService.save(kitchenActual.get());
            return ResponseEntity.accepted().body(kitchenSaved);
        }
        return ResponseEntity.notFound().build();

    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<?> delete(@PathVariable long orderId){
 		try{
            registerOrderService.delete(orderId);
            return ResponseEntity.noContent().build();
        } catch (EntityInUseException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }

    }

}

