package com.jaygibran.deliveryfood.infrastructure.service.storage;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.jaygibran.deliveryfood.core.storage.StorageProperties;
import com.jaygibran.deliveryfood.domain.service.PhotoStorageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;

@AllArgsConstructor
public class S3PhotoStorageService implements PhotoStorageService {

    private final AmazonS3 amazonS3;
    private final StorageProperties storageProperties;

    @Override
    public RecoveredPhoto recover(String fileName) {
        String pathFile = getPathFile(fileName);

        URL url = amazonS3.getUrl(storageProperties.getS3().getBucket(), pathFile);

        RecoveredPhoto recoveredPhoto = RecoveredPhoto.builder()
                .url(url.toString())
                .build();

        return recoveredPhoto;
    }

    @Override
    public void storage(NewPhoto newPhoto) {
        try {

            String filePath = getPathFile(newPhoto.getFileName());

            var objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(newPhoto.getContentType());

            var putObjectRequest = new PutObjectRequest(
                    storageProperties.getS3().getBucket(),
                    filePath,
                    newPhoto.getInputStream(),
                    objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead);

            amazonS3.putObject(putObjectRequest);
        } catch (Exception e) {
            throw new StorageException("It was not possible to sent the file to Amazon S3", e);
        }
    }

    @Override
    public void remove(String fileName) {
        try {
            String filePath = getPathFile(fileName);

            var deleteObjectRequest = new DeleteObjectRequest(storageProperties.getS3().getBucket(), filePath);

            amazonS3.deleteObject(deleteObjectRequest);
        } catch (Exception e) {
            throw new StorageException("It was not possible to remove the file from Amazon S3", e);
        }
    }

    private String getPathFile(String fileName) {
        return String.format("%s/%s", storageProperties.getS3().getDirectoryPhotos(), fileName);
    }
}
