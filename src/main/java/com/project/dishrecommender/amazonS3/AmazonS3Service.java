package com.project.dishrecommender.amazonS3;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.project.dishrecommender.exception.AmazonException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class AmazonS3Service {
    private final AmazonS3 amazonS3;
    private final String bucketName;

    public AmazonS3Service(AmazonS3 amazonS3, @Value("${aws.s3.bucket.name}") String bucketName) {
        this.amazonS3 = amazonS3;
        this.bucketName = bucketName;
    }

    public void delete(AmazonS3Object amazonS3Object) {
        try {
            amazonS3.deleteObject(amazonS3Object.bucket(), amazonS3Object.key());
        } catch (AmazonServiceException e) {
            throw new AmazonException(e.getMessage());
        }
    }

    public AmazonS3Object upload(MultipartFile file) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());

        String fileName = UUID.randomUUID() + ".jpeg";

        try {
            amazonS3.putObject(bucketName, fileName, file.getInputStream(), objectMetadata);
            return new AmazonS3Object(bucketName, fileName);
        } catch (IOException | AmazonServiceException e) {
            throw new AmazonException(e.getMessage());
        }
    }

}
