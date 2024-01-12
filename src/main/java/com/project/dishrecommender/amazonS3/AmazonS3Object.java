package com.project.dishrecommender.amazonS3;

import com.amazonaws.services.s3.model.ObjectMetadata;

public record AmazonS3Object(
        String bucket,
        String key) {
}
