package com.woozuda.backend.image.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.Permission;
import com.woozuda.backend.image.config.S3Client;
import com.woozuda.backend.image.dto.ImageDto;
import com.woozuda.backend.image.entity.Image;
import com.woozuda.backend.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final S3Client s3Client;
    private final ImageRepository imageRepository;



    @Transactional
    public ImageDto uploadImage(MultipartFile multipartFile) throws IOException {

        // 올릴 이미지가 첨부되지 않았을 경우
        if (multipartFile == null || multipartFile.isEmpty()) {
            return null;
        }

        AmazonS3 s3 = s3Client.getAmazonS3();

        // 파일 이름 추출
        String filename = multipartFile.getOriginalFilename();
        String bucketname = s3Client.getBucketName();

        // 메타데이터 생성
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        // object storage 에 그림 올리기
        System.out.println(bucketname);
        s3.putObject(bucketname, filename, multipartFile.getInputStream(), metadata);

        // 올린 파일에 read 권한을 모든 유저에게 준다
        AccessControlList accessControlList = s3Client.getAmazonS3().getObjectAcl(bucketname, filename);
        accessControlList.grantPermission(GroupGrantee.AllUsers, Permission.Read);
        s3Client.getAmazonS3().setObjectAcl(bucketname, filename, accessControlList);

        String imgUrl = s3Client.getAmazonS3().getUrl(bucketname, filename).toString();

        imageRepository.save(Image.of(imgUrl, false));

        return new ImageDto(imgUrl);
    }
}
