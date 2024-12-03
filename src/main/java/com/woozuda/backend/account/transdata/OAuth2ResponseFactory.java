package com.woozuda.backend.account.transdata;

import java.util.Map;

public class OAuth2ResponseFactory {
    public static OAuth2Response getOAuth2Response(String provider, Map<String, Object> attributes) {
        if(provider.equals("google")){
            return new GoogleResponse(attributes);
        }else if(provider.equals("naver")){
            return new NaverResponse(attributes);
        }else if(provider.equals("kakao")){
            return new KakaoResponse(attributes);
        }else{
            return null;
        }
    }
}
