package com.isfood.infrastructure.repository.service.storage;

import com.isfood.domain.exception.ControllerException;

public class StorageException extends Exception {

    public StorageException(String s) {
        super(s);
    }

    public StorageException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
