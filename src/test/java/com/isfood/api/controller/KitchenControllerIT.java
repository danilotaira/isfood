package com.isfood.api.controller;

import static com.isfood.core.utils.JsonConvertionUtils.asJsonString;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.isfood.domain.entity.Kitchen;
import com.isfood.domain.service.RegisterKitchenService;
import com.isfood.utils.DatabaseCleaner;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

//@WebMvcTest
@SpringBootTest	(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ExtendWith(MockitoExtension.class)
@TestPropertySource("/application-test.properties")
public class KitchenControllerIT {
	
	@LocalServerPort
	private int port;
	
	private static final String URL_KITCHENS = "/kitchens";

	private MockMvc mockMvc;
	
	Kitchen kitchen;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private RegisterKitchenService registerKitchenService;
	
	@InjectMocks
    private KitchenController kitchenController;
	
    @BeforeEach
    void setUp() {  
    	kitchen = new Kitchen();
    	kitchen.setName("test");
    	databaseCleaner.clearTables();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = URL_KITCHENS;
    }
    
	@Test
	public void whenGETListWitchKitchensIsCalled_ThenOkStatusIsReturned() throws Exception{
        given()
        	.accept(ContentType.JSON)
        .when()
        	.get()
        .then()
        	.statusCode(HttpStatus.OK.value());
        
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
	public void whenPOSTIsCalled_ThenAKitchenIsCreateds() throws Exception{
        //when
        when(registerKitchenService.save(kitchen)).thenReturn(kitchen);

        // then
        mockMvc.perform(MockMvcRequestBuilders.post(URL_KITCHENS)
        		.accept(MediaType.APPLICATION_JSON)
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(asJsonString(kitchen)))     
        		.andExpect(status().isCreated())	
        		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(kitchen.getName())));
	}		
	
	
    public void prepareData() {
   	 
    	Kitchen kitchen = new Kitchen();
    	kitchen.setName("Brasileira");
    	registerKitchenService.save(kitchen);
    	
    	Kitchen kitchen2 = new Kitchen();
    	kitchen2.setName("Tailandesa");
    	registerKitchenService.save(kitchen2);
    }
}
