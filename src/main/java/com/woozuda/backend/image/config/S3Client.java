package com.woozuda.backend.image.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class S3Client {
    @Value("${ncp.storage.accessKey}")
    private String accessKey;

    @Value("${ncp.storage.secretKey}")
    private String secretKey;

    @Value("${ncp.storage.endpoint}")
    private String endPoint;

    @Value("${ncp.storage.region}")
    private String region;

    @Value("${ncp.storage.bucketName}")
    private String bucketName;

    public AmazonS3 getAmazonS3() {
        return AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endPoint, region))
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                .build();
    }
}
