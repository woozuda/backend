package com.woozuda.backend.shortlink.Controller;


import com.woozuda.backend.shortlink.dto.NoteIdDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/shortlink")
@RestController
@RequiredArgsConstructor
public class LinkController {

    @PostMapping("/new")
    public ResponseEntity<Void> makeSharedNote() {

    }
}
