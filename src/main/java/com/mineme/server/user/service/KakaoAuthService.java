package com.mineme.server.user.service;


import com.mineme.server.entity.User;
import com.mineme.server.security.config.Properties;
import com.mineme.server.security.handler.JwtTokenProvider;
import com.mineme.server.user.dto.KakaoUserDto;
import com.mineme.server.user.dto.UserJwtDto;
import com.mineme.server.user.dto.UserSignRequestDto;
import com.mineme.server.user.repository.UserRepository;
import com.mineme.server.user.util.HttpClientUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;


@Service
@Slf4j
@RequiredArgsConstructor
public class KakaoAuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final Properties properties;



    @Transactional
    public UserJwtDto getKakaoUserDetails(UserSignRequestDto dto){
        Mono<KakaoUserDto> monoUser = HttpClientUtil.getMonoUser(dto);
        KakaoUserDto user = monoUser.block();
        User signedUser = userRepository.findByUsername(user.getId())
                        .orElse(userRepository.save(User.toPendingUserEntity(user.getId(), dto)));

        String accessToken = jwtTokenProvider.create(signedUser.getUsername(), properties.getSecret());

        return new UserJwtDto(accessToken, signedUser.getUserCode());
    }
}
