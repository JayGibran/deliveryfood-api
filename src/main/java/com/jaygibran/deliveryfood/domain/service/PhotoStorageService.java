package com.jaygibran.deliveryfood.domain.service;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;
import java.util.UUID;

public interface PhotoStorageService {

    void storage(NewPhoto newPhoto);

    void remove(String fileName);

    default String generateFileName(String originalName) {
        return UUID.randomUUID() + "_" + originalName;
    }

    default void replace(String existingFileName, NewPhoto newPhoto) {
        this.storage(newPhoto);
        
        if (existingFileName != null) {
            this.remove(existingFileName);
        }
    }

    @Getter
    @Builder
    class NewPhoto {
        private String fileName;
        private InputStream inputStream;
    }
}
