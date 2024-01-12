package com.project.dishrecommender.amazonS3;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonS3Config {
    private final String accessKey;
    private final String secretKey;

    public AmazonS3Config(
            @Value("${aws.s3.access.key}") String accessKey,
            @Value("${aws.s3.secret.key}") String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    @Bean
    public AmazonS3 s3() {
        AWSCredentials awsCredentials =
                new BasicAWSCredentials(accessKey, secretKey);
        return AmazonS3ClientBuilder
                .standard()
                .withRegion("eu-central-1")
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();

    }
}
