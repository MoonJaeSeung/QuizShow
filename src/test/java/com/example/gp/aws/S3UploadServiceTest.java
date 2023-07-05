package com.example.gp.aws;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;



@ExtendWith(SpringExtension.class)
@SpringBootTest
@RequiredArgsConstructor
@Slf4j
class S3UploadServiceTest {

    @Autowired
    private S3UploadService s3UploadService;
    private final AmazonS3Client amazonS3Client = new AmazonS3Client();

    @DisplayName("저장된 이미지 찾기")
    @Test
    public void findImg() {
        String imgPath = s3UploadService.getThumbnailPath("gomasd.jpg");
        log.info(imgPath);
        Assertions.assertThat(imgPath).isNotNull();
    }
    @Test
    public void listFiles() {
        ListObjectsV2Result result = amazonS3Client.listObjectsV2("s3quiz");
        for (S3ObjectSummary objectSummary : result.getObjectSummaries()) {
            System.out.println(objectSummary.getKey());
        }
    }
}