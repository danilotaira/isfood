package com.isfood.domain.exception;

public class PermissionNotFoundException extends EntityNotFoundException{
    /**
	 *
	 */
	private static final long serialVersionUID = 2198794498640896764L;
	public PermissionNotFoundException(String message) {
        super(message);
    }
    public PermissionNotFoundException(Integer id) {
        super(String.format("There is no Permission register with the code= %d", id));
    }

}

