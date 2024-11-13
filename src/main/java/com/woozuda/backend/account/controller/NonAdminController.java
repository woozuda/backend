package com.woozuda.backend.account.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NonAdminController {

    @GetMapping("/account/sample/notadmin")
    public String mainP(){
        return "not admin controller";
    }
}
