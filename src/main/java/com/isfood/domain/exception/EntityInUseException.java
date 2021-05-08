package com.isfood.domain.exception;

public class EntityInUseException extends ControllerException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EntityInUseException(String message){
        super(message);
    }
}

