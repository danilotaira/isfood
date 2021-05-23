package com.isfood.domain.exception;

public class UserAccessNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;
	public UserAccessNotFoundException(String message) {
        super(message);
    }
    public UserAccessNotFoundException(Integer id) {
        super(String.format("There is no User register with the code= %d", id));
    }

}
