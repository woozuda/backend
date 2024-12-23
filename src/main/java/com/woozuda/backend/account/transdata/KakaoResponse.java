package com.woozuda.backend.account.transdata;

import java.util.Map;

public class KakaoResponse implements OAuth2Response {
    private String provider;
    private String providerId;
    private String email;
    private String name;

    // kakao : {id=3821328965, connected_at=2024-12-05T07:19:11Z, properties={nickname=이동현}, kakao_account={profile_nickname_needs_agreement=false, profile={nickname=이동현, is_default_nickname=false}, has_email=true, email_needs_agreement=false, is_email_valid=true, is_email_verified=true, email=enjoying1018@daum.net}}
    public KakaoResponse(Map<String, Object> attribute){
        this.provider = "kakao";
        this.providerId = attribute.get("id").toString();
        this.email = ((Map) attribute.get("kakao_account")).get("email").toString();
        this.name = "";
    }

    @Override
    public String getProvider() {
        return provider;
    }

    @Override
    public String getProviderId() {
        return providerId;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getName() {
        return name;
    }
}
