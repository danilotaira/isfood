package com.isfood.domain.exception;

public class StateNotFoundException extends EntityNotFoundException{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StateNotFoundException(String message) {
        super(message);
    }

    public StateNotFoundException(Integer id) {
        super(String.format("There is no State register with the code= %d", id));
    }
}

