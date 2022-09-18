package com.jaygibran.deliveryfood.infrastructure.service.storage;

import com.amazonaws.services.s3.AmazonS3;
import com.jaygibran.deliveryfood.domain.service.PhotoStorageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@AllArgsConstructor
@Service
public class S3PhotoStorageService implements PhotoStorageService {

    private final AmazonS3 amazonS3;

    @Override
    public InputStream recover(String fileName) {
        return null;
    }

    @Override
    public void storage(NewPhoto newPhoto) {

    }

    @Override
    public void remove(String fileName) {

    }
}
