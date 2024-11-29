package com.woozuda.backend.account.service;

import com.woozuda.backend.account.dto.*;
import com.woozuda.backend.account.entity.UserEntity;
import com.woozuda.backend.account.repository.UserRepository;
import com.woozuda.backend.account.transdata.GoogleResponse;
import com.woozuda.backend.account.transdata.KakaoResponse;
import com.woozuda.backend.account.transdata.NaverResponse;
import com.woozuda.backend.account.transdata.OAuth2Response;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response;

        System.out.println("출력이되는~~");
        System.out.println(oAuth2User.getAttributes());

        /*
        System.out.println(oAuth2User.getAttributes()); 시 출력 내용

        google : {sub=114239676830803412592, name=Dong Hyeon Lee, given_name=Dong Hyeon, family_name=Lee, picture=https://lh3.googleusercontent.com/a/ACg8ocIkBClm_OfS47EdZK209e7dnd-ZcSiJjLvJHQjhoTx5CGcGrw=s96-c, email=ske04186@gmail.com, email_verified=true}
        naver: {resultcode=00, message=success, response={id=oz7UDpVICFBzNmLB-mwZHzgR7IIY7-Y02Az8vYPMwyY, email=enjoying1018@naver.com, name=이동현}}
        kakao : {id=3812693211, connected_at=2024-11-29T08:37:05Z, properties={nickname=이동현}, kakao_account={profile_nickname_needs_agreement=false, profile={nickname=이동현, is_default_nickname=false}}}
         */
        if(registrationId.equals("naver")){
            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        }else if(registrationId.equals("google")){
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        }else if(registrationId.equals("kakao")){
            oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());
        }else{
            return null;
        }

        String username = oAuth2Response.getProvider()+" "+oAuth2Response.getProviderId();
        UserEntity existData = userRepository.findByUsername(username);

        if (existData == null) {

            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(username);
            //userEntity.setEmail(oAuth2Response.getEmail());
            //userEntity.setName(oAuth2Response.getName());
            userEntity.setRole("ROLE_ADMIN");

            userRepository.save(userEntity);

            return new CustomOAuth2User(userEntity);
        }else{
            return new CustomOAuth2User(existData);
        }
    }
}
