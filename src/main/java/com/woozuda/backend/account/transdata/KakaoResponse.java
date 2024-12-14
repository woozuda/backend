package com.woozuda.backend.account.transdata;

import java.util.Map;

public class KakaoResponse implements OAuth2Response {
    private String provider;
    private String providerId;
    private String email;
    private String name;

    // kakao : {id=3812693211, connected_at=2024-11-29T08:37:05Z, properties={nickname=이동현}, kakao_account={profile_nickname_needs_agreement=false, profile={nickname=이동현, is_default_nickname=false}}}
    public KakaoResponse(Map<String, Object> attribute){
        this.provider = "kakao";
        this.providerId = attribute.get("id").toString();
        this.email = "";
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
