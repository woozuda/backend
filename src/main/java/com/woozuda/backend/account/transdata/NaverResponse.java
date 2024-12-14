package com.woozuda.backend.account.transdata;

import java.util.Map;

public class NaverResponse implements OAuth2Response {

    //private final Map<String, Object> attribute;

    private String provider;
    private String providerId;
    //private String email;
    //private String name;


    public NaverResponse(Map<String, Object> attribute){
        // email, name은 수집하지 않기로 결정.
        // naver: {resultcode=00, message=success, response={id=oz7UDpVICFBzNmLB-mwZHzgR7IIY7-Y02Az8vYPMwyY , email=enjoying1018@naver.com, name=이동현}}

        Map<String, Object> attribute_res = (Map<String, Object>) attribute.get("response");
        this.provider = "naver";
        this.providerId = attribute_res.get("id").toString();
        //this.email = attribute_res.get("email").toString();
        //this.name = attribute_res.get("name").toString();
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
        return "";
    }

    @Override
    public String getName() {
        return "";
    }
}
