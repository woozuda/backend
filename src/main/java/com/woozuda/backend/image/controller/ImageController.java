package com.woozuda.backend.image.controller;

import com.woozuda.backend.account.dto.CustomUser;
import com.woozuda.backend.image.dto.ImageDto;
import com.woozuda.backend.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequestMapping("/api/image")
@RequiredArgsConstructor
@RestController
public class ImageController {

    private final ImageService imageService;

    // 이미지 단건 업로드
    @PostMapping("/upload")
    public ResponseEntity<ImageDto> uploadImage(MultipartFile multipartFile) throws IOException {
        ImageDto responseDto = imageService.uploadImage(multipartFile);
        return ResponseEntity.ok(responseDto);
    }

    /*
    @PostMapping("/delete")
    public void deleteImage(){
        String url = "https://kr.object.ncloudstorage.com/woozuda-image/test-dummy.png";
        imageService.deleteImage(url.split("/")[4]);
    }
    */

    // 랜덤 이미지 추출
    @GetMapping("/random")
    public ResponseEntity<ImageDto> getRandomImage(){
        ImageDto responseDto = imageService.getRandomImage();
        return ResponseEntity.ok(responseDto);
    }
}
