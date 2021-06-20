package com.isfood.domain.service;

import com.isfood.infrastructure.repository.service.storage.StorageException;
import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;
import java.util.UUID;

public interface PhotoStorageService {
    InputStream restore (String nameFile) throws StorageException;

    void storage(NewPhoto newPhoto) throws StorageException;

    void remove(String nameFile) throws StorageException;

    default String generateNameFile(String nameOriginal){
        return UUID.randomUUID().toString()+"_"+nameOriginal;
    }

    @Getter
    @Builder
    class NewPhoto{
        private String nameFile;
        private InputStream inputStream;
    }
}
