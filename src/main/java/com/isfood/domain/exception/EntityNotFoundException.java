package com.isfood.domain.exception;

public abstract class EntityNotFoundException extends ControllerException {

	private static final long serialVersionUID = 1L;

	public EntityNotFoundException(String message) {
        super(message);
    }
}

