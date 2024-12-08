package com.woozuda.backend.account.transdata;

import java.util.Map;

public class GoogleResponse implements OAuth2Response {

    private String provider;
    private String providerId;
    private String email;
    private String name;

    //google : {sub=114239676830803412592, name=Dong Hyeon Lee, given_name=Dong Hyeon, family_name=Lee, picture=https://lh3.googleusercontent.com/a/ACg8ocIkBClm_OfS47EdZK209e7dnd-ZcSiJjLvJHQjhoTx5CGcGrw=s96-c, email=ske04186@gmail.com, email_verified=true}
    public GoogleResponse(Map<String, Object> attribute) {
        this.provider = "google";
        this.providerId = attribute.get("sub").toString();
        this.email = attribute.get("email").toString();
        this.name = attribute.get("name").toString();
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
