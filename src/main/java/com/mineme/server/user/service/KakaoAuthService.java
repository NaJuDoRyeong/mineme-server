package com.mineme.server.user.service;


import com.mineme.server.user.dto.KakaoUserDto;
import com.mineme.server.user.repository.UserRepository;
import com.mineme.server.user.util.HttpClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
@Slf4j
@RequiredArgsConstructor
public class KakaoAuthService {

    private final UserRepository userRepository;

    public Mono<KakaoUserDto> getKakaoUserDetails(String token){
        return HttpClient.getClient("https://kapi.kakao.com")
                .get()
                .uri("/v2/user/me")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8")
                .retrieve()
                .bodyToMono(KakaoUserDto.class);
    }
}
