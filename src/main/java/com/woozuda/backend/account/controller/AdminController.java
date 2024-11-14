package com.woozuda.backend.account.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @GetMapping("/account/sample/admin")
    public String adminP(){
        return "admin controller";
    }
}
