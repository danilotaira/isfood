package com.isfood.domain.exception;

public class KitchenNotFoundException extends EntityNotFoundException{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public KitchenNotFoundException(String message) {
        super(message);
    }
    public KitchenNotFoundException(Long id) {
        super(String.format("There is no kitchen register with the code= %d", id));
    }

}

