package com.jaygibran.deliveryfood.core.storage;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.jaygibran.deliveryfood.domain.service.PhotoStorageService;
import com.jaygibran.deliveryfood.infrastructure.service.storage.LocalStorageService;
import com.jaygibran.deliveryfood.infrastructure.service.storage.S3PhotoStorageService;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@AllArgsConstructor
@Configuration
public class StorageConfig {

    private final StorageProperties storageProperties;

    @Bean
    @ConditionalOnProperty(name = "deliveryfood.storage.type", havingValue = "s3")
    public AmazonS3 amazonS3() {

        var credentials = new BasicAWSCredentials(
                storageProperties.getS3().getAccessKeyId(), storageProperties.getS3().getSecretAccessKey());

        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(storageProperties.getS3().getRegion())
                .build();
    }

    @Bean
    public PhotoStorageService photoStorageService(AmazonS3 amazonS3) {
        if (StorageProperties.StorageType.S3.equals(storageProperties.getType())) {
            return new S3PhotoStorageService(amazonS3, storageProperties);
        } else {
            return new LocalStorageService(storageProperties);
        }
    }
}
