package com.s3.s3_demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.nio.ByteBuffer;

@Service
public class S3Service {

    private final S3Client s3Client;
    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }


    public String putObject(MultipartFile file, String key) {

        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        try {
            byte[] bytes = file.getBytes();
            this.s3Client.putObject(objectRequest, RequestBody.fromByteBuffer(ByteBuffer.wrap(bytes)));
        } catch (IOException | S3Exception e) {
            System.out.println(e.getMessage());
        }

        return key;
    }


    public ResponseInputStream<GetObjectResponse> getObject(String key) {

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        try {
            return s3Client.getObject(getObjectRequest);
        } catch (S3Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public void deleteObject(String key) {

        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        try {
            this.s3Client.deleteObject(deleteObjectRequest);
        } catch (S3Exception e) {
            System.out.println(e.getMessage());
        }

    }


}
