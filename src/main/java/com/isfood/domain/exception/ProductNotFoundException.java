package com.isfood.domain.exception;

public class ProductNotFoundException extends EntityNotFoundException{
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public ProductNotFoundException(String message) {
        super(message);
    }
    public ProductNotFoundException(Long restaurantId, Long productId) {
        super(String.format("There is no product register with the code= %d for restaurant = %d", productId, restaurantId));
    }

}

