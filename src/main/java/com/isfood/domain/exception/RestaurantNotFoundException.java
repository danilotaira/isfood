package com.isfood.domain.exception;

public class RestaurantNotFoundException extends EntityNotFoundException{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public RestaurantNotFoundException(String message) {
        super(message);
    }
    public RestaurantNotFoundException(Long id) {
        super(String.format("There is no restaurant register with the code= %d", id));
    }

}

