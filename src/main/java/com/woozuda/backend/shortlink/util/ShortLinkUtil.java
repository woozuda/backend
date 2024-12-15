package com.woozuda.backend.shortlink.util;

import com.woozuda.backend.account.entity.UserEntity;
import com.woozuda.backend.shortlink.dto.ShortLinkDto;
import com.woozuda.backend.shortlink.entity.ShortLink;
import com.woozuda.backend.shortlink.repository.ShortLinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Random;

import static com.woozuda.backend.account.entity.QUserEntity.userEntity;

@Component
@RequiredArgsConstructor
public class ShortLinkUtil {

    private final ShortLinkRepository shortLinkRepository;

    public void saveShortLink(UserEntity userEntity){

        // 이미 해당 회원에 숏링크가 있으면 아무일도 하지 않음
        if(shortLinkRepository.findByUserEntity(userEntity) != null){
            return;
        }

        String newShortLink = "";

        while(true){

            // 숏링크 생성
            newShortLink = createRandomLink();

            // 중복 검사 (다른 유저랑 동등한 숏링크 인지?)
            if(shortLinkRepository.findByUrl(newShortLink) == null){
                break;
            }
        }

        ShortLink shortLink = new ShortLink(null, newShortLink, userEntity);

        shortLinkRepository.save(shortLink);
    }

    public String createRandomLink(){

        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int strLength = str.length();

        Random random = new Random();

        StringBuffer randomStr = new StringBuffer();

        for (int i = 0; i < 8; i++) {
            randomStr.append(str.charAt(random.nextInt(strLength)));
        }

        return randomStr.toString();
    }
}
