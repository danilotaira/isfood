package com.isfood.api.controller;

import static com.isfood.core.utils.JsonConvertionUtils.asJsonString;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import com.isfood.domain.entity.Kitchen;
import com.isfood.domain.service.RegisterKitchenService;
import com.isfood.utils.DatabaseCleaner;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

//@WebMvcTest
@SpringBootTest	(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ExtendWith(MockitoExtension.class)
@TestPropertySource("/application-test.properties")
public class KitchenControllerRestAssuredIT {
	
	private static final int KITCHEN_ID_INEXISTENT = 100;

	@LocalServerPort
	private int port;
	
	private static final String URL_KITCHENS = "/kitchens";

	Kitchen kitchen, kitchenGet;
	Integer registers;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private RegisterKitchenService registerKitchenService;
	
    @BeforeEach
    void setUp() {  
    	kitchen = new Kitchen();
    	kitchen.setName("test");
    	databaseCleaner.clearTables();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = URL_KITCHENS;
        prepareData();
    }
    
	@Test
	public void whenGETListWitchKitchensIsCalled_ThenOkStatusAndLenthListIsReturned() throws Exception{
        given()
        	.accept(ContentType.JSON)
        .when()
        	.get()
        .then()
        	.body("", hasSize(registers))
        	.statusCode(HttpStatus.OK.value())
        	.log().body();
        
	}
	
	@Test
	public void whenPOSTIsCalled_ThenAKitchenIsCreated() throws Exception{
        given()
        	.body(asJsonString(kitchen))
        	.contentType(ContentType.JSON)
        	.accept(ContentType.JSON)
        .when()
        	.post()
        .then()
        	.statusCode(HttpStatus.CREATED.value());
	}	
	
	@Test
	public void whenGETwithIdIsCalled_ThenAKitchenIsReturned() throws Exception{
        given()
        	.pathParam("kitchenId", kitchenGet.getId())
    		.accept(ContentType.JSON)
		.when()
    		.get("/{kitchenId}")
		.then()
    		.statusCode(HttpStatus.OK.value())
    		.body("name", is(kitchenGet.getName()))
    		.log().body();
	}		

	@Test
	public void whenGETwithIdInexistentIsCalled_ThenAKitchenIsReturned() throws Exception{
        given()
        	.pathParam("kitchenId", KITCHEN_ID_INEXISTENT)
    		.accept(ContentType.JSON)
		.when()
    		.get("/{kitchenId}")
		.then()
    		.statusCode(HttpStatus.NOT_FOUND.value())
    		.log().body()
    		.log().headers()
    		.log().all();
	}		
	
	
    public void prepareData() {
   	 
    	Kitchen kitchen = new Kitchen();
    	kitchen.setName("Brasileira");
    	registerKitchenService.save(kitchen);
    	
    	Kitchen kitchen2 = new Kitchen();
    	kitchen2.setName("Tailandesa");
    	registerKitchenService.save(kitchen2);
    	
    	kitchenGet = new Kitchen();
    	kitchenGet.setName("Americana");
    	kitchenGet = registerKitchenService.save(kitchenGet);
    	
    	List<Kitchen> findAll = registerKitchenService.findAll();
    	registers = findAll.size();
    }
}
