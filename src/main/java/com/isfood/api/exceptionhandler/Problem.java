package com.isfood.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Problem {

    private Integer status;
    private String type;
    private String title;
    private String detail;
    
    private String userMessage;
    private OffsetDateTime timestamp;
    private List<Object> objects;
    
    @Getter
    @Builder
    public static class Object {
    	
    	private String name;
    	private String userMessage;
    	
    }       
}

