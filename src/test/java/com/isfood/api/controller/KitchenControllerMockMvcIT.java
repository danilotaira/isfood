package com.isfood.api.controller;

import static com.isfood.core.utils.JsonConvertionUtils.asJsonString;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.isfood.domain.entity.Kitchen;
import com.isfood.domain.service.RegisterKitchenService;
import com.isfood.utils.DatabaseCleaner;

@SpringBootTest	()
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
public class KitchenControllerMockMvcIT {
	
	private static final String URL_KITCHENS = "/kitchens";
	
	@Autowired
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
    	prepareData();
    }
    
	@Test
	public void whenGETListWitchKitchensIsCalled_ThenOkStatusIsReturned() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get(URL_KITCHENS)
        		.accept(MediaType.APPLICATION_JSON))
        		.andDo(print())
        		.andExpect(status().isOk())	
        		.andExpect(content().contentType(MediaType.APPLICATION_JSON))        		
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(notNullValue())))
                .andExpect(jsonPath("$[0]").value(hasKey("name")));
        
	}
	
	@Test
	public void whenPOSTIsCalled_ThenAKitchenIsCreated() throws Exception{				
        mockMvc.perform(MockMvcRequestBuilders.post(URL_KITCHENS)
        		.accept(MediaType.APPLICATION_JSON)
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(asJsonString(kitchen)))     
        		.andExpect(status().isCreated())	
        		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(kitchen.getName())));
	}	
	
	@Test
	public void whenPOSTIsCalled_ThenAKitchenIsCreateds() throws Exception{
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
