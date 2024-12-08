package com.woozuda.backend.account.service;

import com.woozuda.backend.account.dto.*;
import com.woozuda.backend.account.entity.UserEntity;
import com.woozuda.backend.account.repository.UserRepository;
import com.woozuda.backend.account.transdata.*;
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

        String provider = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response;

        oAuth2Response = OAuth2ResponseFactory.getOAuth2Response(provider, oAuth2User.getAttributes());

        String username = oAuth2Response.getProvider()+" "+oAuth2Response.getProviderId();
        UserEntity existData = userRepository.findByUsername(username);

        if (existData == null) {

            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(username);
            //userEntity.setEmail(oAuth2Response.getEmail());
            //userEntity.setName(oAuth2Response.getName());
            userEntity.setRole("ROLE_ADMIN");

            userRepository.save(userEntity);

            return new CustomUser(userEntity);
        }else{
            return new CustomUser(existData);
        }
    }
}
