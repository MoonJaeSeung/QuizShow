package com.example.gp.aws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.util.Base64;

@Controller
public class ImageController {
    private final S3ImageService s3ImageService;

    @Autowired
    public ImageController(S3ImageService s3ImageService) {
        this.s3ImageService = s3ImageService;
    }

    @GetMapping("/image")
    public String showImage(Model model) {
        System.out.println("ImageController.showImage");
        String bucketName = "s3quiz"; // S3 버킷 이름
        String objectKey = "gom.jpg"; // 이미지가 저장된 경로

        byte[] imageBytes = s3ImageService.loadImage(bucketName, objectKey);
        if (imageBytes != null) {
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            model.addAttribute("image", base64Image);
        }
        return "/game/game2";
    }
}