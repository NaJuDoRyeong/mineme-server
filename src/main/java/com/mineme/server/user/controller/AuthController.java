package com.mineme.server.user.controller;


import com.mineme.server.user.dto.KakaoUserDto;
import com.mineme.server.user.service.KakaoAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@RestController
@RequiredArgsConstructor
@RequestMapping
public class AuthController {

    private final KakaoAuthService kakaoAuthService;

    @GetMapping("user")
    public Mono<KakaoUserDto> kakaoUserDetails(@RequestParam String token){
        return kakaoAuthService.getKakaoUserDetails(token);
    }
}
