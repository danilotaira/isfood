package com.isfood.domain.exception;

public class CityNotFoundException extends EntityNotFoundException{
    /**
	 * 
	 */
	private static final long serialVersionUID = 2198794498640896764L;
	public CityNotFoundException(String message) {
        super(message);
    }
    public CityNotFoundException(Integer id) {
        super(String.format("There is no City register with the code= %d", id));
    }

}

