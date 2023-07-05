package com.example.gp.aws;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class S3UploadService {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public String getThumbnailPath(String path) {
        return amazonS3Client.getUrl(bucketName, path).toString();
    }

    public void listFiles() {
        ListObjectsV2Request request = new ListObjectsV2Request()
                .withBucketName(bucketName)
                .withDelimiter("/")
                .withPrefix("");  // 빈 문자열로 설정하여 루트 폴더를 의미함

        ListObjectsV2Result result;
        do {
            result = amazonS3Client.listObjectsV2(request);

            for (S3ObjectSummary objectSummary : result.getObjectSummaries()) {
                String fileUrl = "https://s3.amazonaws.com/" + bucketName + "/" + objectSummary.getKey();
                System.out.println("fileUrl = " + fileUrl);
            }
            String token = result.getNextContinuationToken();
            request.setContinuationToken(token);
        } while (result.isTruncated());

    }
}
