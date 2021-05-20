package com.isfood.domain.exception;

public class GroupAccessNotFoundException extends EntityNotFoundException {
	
	private static final long serialVersionUID = 1L;
	public GroupAccessNotFoundException(String message) {
        super(message);
    }
    public GroupAccessNotFoundException(Integer id) {
        super(String.format("There is no Group register with the code= %d", id));
    }

}
