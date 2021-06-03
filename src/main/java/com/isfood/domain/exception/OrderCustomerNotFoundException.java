package com.isfood.domain.exception;

public class OrderCustomerNotFoundException extends EntityNotFoundException{
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public OrderCustomerNotFoundException(String uuid) {
        super(String.format("There is no order register with the code= %s .", uuid));
    }

}

