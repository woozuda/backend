package com.woozuda.backend.account.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AllUserController {

    @GetMapping("/account/sample/alluser")
    public String allP(){
        return "all user can access this page!";
    }
}
