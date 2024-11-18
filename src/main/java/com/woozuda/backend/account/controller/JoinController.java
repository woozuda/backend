package com.woozuda.backend.account.controller;

import com.woozuda.backend.account.dto.JoinDTO;
import com.woozuda.backend.account.service.JoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JoinController {

    private final JoinService joinService;

    public JoinController(JoinService joinService){
        this.joinService = joinService;
    }

    @PostMapping("/account/join")
    public String joinProcess(JoinDTO joinDTO){

        joinService.joinProcess(joinDTO);

        return "ok";
    }
}
