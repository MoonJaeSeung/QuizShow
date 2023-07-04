package com.example.gp.aws;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

@Service
public class S3ImageService {
    private final S3Client s3Client;

    public S3ImageService() {

        String accessKeyId = "AKIAQGXRTI4MWFRP27O7";
        String secretAccessKey = "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";

        // S3 클라이언트 초기화
        this.s3Client = S3Client.builder()
                .region(Region.AP_NORTHEAST_2) // S3 버킷이 있는 리전
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKeyId, secretAccessKey)))
                .build();
    }

    public byte[] loadImage(String bucketName, String objectKey) {
        try {
            // S3에서 이미지 로드
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .build();
            ResponseBytes<GetObjectResponse> responseBytes = s3Client.getObjectAsBytes(getObjectRequest);
            return responseBytes.asByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
