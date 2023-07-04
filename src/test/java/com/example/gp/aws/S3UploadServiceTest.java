package com.example.gp.aws;

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
@Slf4j
class S3UploadServiceTest {

    @Autowired
    private S3UploadService s3UploadService;

    @DisplayName("저장된 이미지 찾기")
    @Test
    public void findImg() {
        String imgPath = s3UploadService.getThumbnailPath("gom.jpg");
        log.info(imgPath);
        Assertions.assertThat(imgPath).isNotNull();
    }
}