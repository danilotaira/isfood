package com.isfood;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.isfood.domain.entity.Kitchen;
import com.isfood.domain.exception.EntityInUseException;
import com.isfood.domain.exception.KitchenNotFoundException;
import com.isfood.domain.service.RegisterKitchenService;

@SpringBootTest
class ExampleRegisterKitcheServiceIT {
	
	@Autowired
	private RegisterKitchenService registerKitchen;

	@Test
	public void whenKitchenInformed_ThenItShouldBeCreated() {
		//given
		Kitchen kitchen = new Kitchen();
		kitchen.setName("Chinesa");
		
		//when
		kitchen = registerKitchen.save(kitchen);
		
		//then
		assertThat(kitchen).isNotNull();
		assertThat(kitchen.getId()).isNotNull();
	}
	
	@Test()
	public void whenKitchenWithoutName_ThenAnExceptionShouldBeThrow() {
		//given
		Kitchen kitchen = new Kitchen();
		kitchen.setName(null);
			
		//then
		assertThrows(ConstraintViolationException.class, () -> registerKitchen.save(kitchen));
	}
	
	@Test
	public void whenToDeleteKitchenInUse_ThenAnExcpetionShouldBeThrow() {
		assertThrows(EntityInUseException.class, () -> registerKitchen.delete(1L));
	}
	
	@Test
	public void whenToDeleteKitchenInexistent_ThenAnExcpetionShouldBeThrow() {
		assertThrows(KitchenNotFoundException.class, () -> registerKitchen.delete(1000L));
	}


}
