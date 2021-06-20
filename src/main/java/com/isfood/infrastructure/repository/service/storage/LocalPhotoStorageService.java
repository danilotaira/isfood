package com.isfood.infrastructure.repository.service.storage;

import com.isfood.domain.service.PhotoStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class LocalPhotoStorageService implements PhotoStorageService {

    @Value("${isfood.storage.local.path-photo}")
    private String pathPhoto;

    @Override
    public InputStream restore(String nameFile) throws StorageException {
        try {
            Path filePath = getFilePath(nameFile);
            return Files.newInputStream(filePath);
        } catch (Exception e) {
            throw new StorageException("Não foi possível recuperar arquivo.", e);
        }
    }

    @Override
    public void storage(NewPhoto newPhoto) throws StorageException {
        try {
            Path filePath = getFilePath(newPhoto.getNameFile());
            FileCopyUtils.copy(newPhoto.getInputStream(),
                    Files.newOutputStream(filePath));
        } catch (Exception e) {
            throw new StorageException("Não foi possível armazer arquivo.", e);
        }
    }

    @Override
    public void remove(String nameFile) throws StorageException {
        try{
            Path filePath = getFilePath(nameFile);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new StorageException("Não foi possível excluir arquivo." , e);
        }
    }

    private Path getFilePath(String nameFile){
        Path path = Paths.get(pathPhoto+nameFile);
        return path;

    }
}
