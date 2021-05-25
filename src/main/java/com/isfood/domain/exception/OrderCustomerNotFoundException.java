package com.isfood.domain.exception;

public class OrderCustomerNotFoundException extends EntityNotFoundException{
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public OrderCustomerNotFoundException(String message) {
        super(message);
    }
    public OrderCustomerNotFoundException(Long orderId) {
        super(String.format("There is no order register with the code= %d .", orderId));
    }

}

