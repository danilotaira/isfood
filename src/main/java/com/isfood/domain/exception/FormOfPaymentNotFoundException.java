package com.isfood.domain.exception;

public class FormOfPaymentNotFoundException extends EntityNotFoundException{
    /**
	 * 
	 */
	private static final long serialVersionUID = 2198794498640896764L;
	public FormOfPaymentNotFoundException(String message) {
        super(message);
    }
    public FormOfPaymentNotFoundException(Integer id) {
        super(String.format("There is no Form Of Payment register with the code= %d", id));
    }

}

