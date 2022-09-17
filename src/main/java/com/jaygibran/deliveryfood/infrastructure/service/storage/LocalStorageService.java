package com.jaygibran.deliveryfood.infrastructure.service.storage;

import com.jaygibran.deliveryfood.domain.service.PhotoStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class LocalStorageService implements PhotoStorageService {

    @Value("${deliveryfood.storage.local.directory-photos}")
    private Path directoryPhotos;

    @Override
    public InputStream recover(String fileName) {
        Path filePath = getFilePath(fileName);
        try {
            return Files.newInputStream(filePath);
        } catch (IOException e) {
            throw new StorageException("It was not possible to recover file", e);
        }
    }

    @Override
    public void storage(NewPhoto newPhoto) {
        Path filePath = getFilePath(newPhoto.getFileName());

        try {
            FileCopyUtils.copy(newPhoto.getInputStream(), Files.newOutputStream(filePath));
        } catch (IOException e) {
            throw new StorageException("It was not possible to storage file", e);
        }
    }

    @Override
    public void remove(String fileName) {
        Path filePath = getFilePath(fileName);
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new StorageException("It was not possible to remove file", e);
        }
    }

    private Path getFilePath(String nameFile) {
        return directoryPhotos.resolve(Path.of(nameFile));
    }
}
