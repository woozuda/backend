package com.woozuda.backend.shortlink.Controller;


import com.woozuda.backend.account.dto.CustomUser;
import com.woozuda.backend.shortlink.Service.ShareService;
import com.woozuda.backend.shortlink.dto.NoteIdDto;
import com.woozuda.backend.shortlink.dto.ShortLinkDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/shortlink")
@RestController
@RequiredArgsConstructor
public class LinkController {

    private final ShareService shareService;

    @PostMapping("/new")
    public ResponseEntity<ShortLinkDto> makeShortLink(@AuthenticationPrincipal CustomUser customUser) {

        String username = customUser.getUsername();

        ShortLinkDto shortlinkDto = shareService.makeShortLink(username);

        return ResponseEntity.status(HttpStatus.OK).body(shortlinkDto);
    }

}
