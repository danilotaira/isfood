package com.isfood.api.controller;

import static com.isfood.core.utils.JsonConvertionUtils.asJsonString;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.isfood.api.assembler.KitchenDTOAssembler;
import com.isfood.api.assembler.KitchenDTODisassembler;
import com.isfood.api.model.KitchenDTO;
import com.isfood.domain.entity.Kitchen;
import com.isfood.domain.service.RegisterKitchenService;

//@WebMvcTest
//@SpringBootTest	
@ExtendWith(MockitoExtension.class)
@TestPropertySource("/application-test.properties")
public class KitchenControllerTest {
	
	private static final String URL_KITCHENS = "/kitchens";

	private MockMvc mockMvc;
	
	Kitchen kitchen = new Kitchen();
	KitchenDTO kitchenDTO = new KitchenDTO();
	
	@Mock
	private RegisterKitchenService registerKitchenService;
	
	@Mock
	private KitchenDTOAssembler kitchenDTOAssembler;	
	
	@Mock
	private KitchenDTODisassembler kitchenDTODisassembler;		
	
	@InjectMocks
    private KitchenController kitchenController;
	
    @BeforeEach
    void setUp() {  
    	kitchen.setName("test");
    	kitchen.setId(1L);
        mockMvc = MockMvcBuilders.standaloneSetup(kitchenController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())                   
                .build();    
        kitchenDTO.setId(1L);
        kitchenDTO.setName("test");
        
    }
	
	@Test
	public void whenGETListWitchKitchensIsCalled_ThenOkStatusIsReturned() throws Exception{
        // given
        kitchen.setId(1L);

        //when
        when(registerKitchenService.findAll()).thenReturn(Collections.singletonList(kitchen));
        when(kitchenDTOAssembler.toCollectionDTO(Collections.singletonList(kitchen))).thenReturn(Collections.singletonList(kitchenDTO));
        
        // then
        mockMvc.perform(MockMvcRequestBuilders.get(URL_KITCHENS)
        		.accept(MediaType.APPLICATION_JSON))              
        		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(kitchen.getId().intValue())))               
                .andExpect(jsonPath("$[0].name", is(kitchen.getName())));
	}
	
	@Test
	public void whenPOSTIsCalled_ThenAKitchenIsCreated() throws Exception{
        //when
		kitchenDTO.setId(null);
    	when(kitchenDTOAssembler.toDTO(any())).thenReturn(kitchenDTO);
    	when(kitchenDTODisassembler.toDomainObject(any())).thenReturn(kitchen);
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
	
	@Test
	public void whenPOSTIsCalled_ThenAKitchenIsCreateds() throws Exception{
        //when
    	when(kitchenDTOAssembler.toDTO(any())).thenReturn(kitchenDTO);
    	when(kitchenDTODisassembler.toDomainObject(any())).thenReturn(kitchen);
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
	

}
