package com.mineme.server.user.service;

import com.mineme.server.common.enums.ErrorCode;
import com.mineme.server.common.exception.CustomException;
import com.mineme.server.entity.User;
import com.mineme.server.security.config.Properties;
import com.mineme.server.security.provider.JwtTokenProvider;
import com.mineme.server.user.dto.Auth;
import com.mineme.server.user.dto.Kakao;
import com.mineme.server.user.repository.UserMatchingCodeRepository;
import com.mineme.server.user.repository.UserRepository;
import com.mineme.server.user.util.AuthClientUtil;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class KakaoAuthService extends AuthService<Auth.SignRequest> {

	@Transactional
	@Override
	public Auth.Jwt getUserDetails(Auth.SignRequest dto) {
		try {
			Kakao.User user = AuthClientUtil.getKakaoUser(dto).block();
			User signedUser = userRepository.findByUsername(user.getId()).orElse(null);

			if (signedUser == null) {
				User pendingUser = User.toPendingUserEntity(user.getId(), dto);
				pendingUser.setUserCode(getUserMatchingCode());
				signedUser = userRepository.save(pendingUser);
			}

			String accessToken = jwtTokenProvider.create(signedUser.getUsername(), signedUser.getUserState(),
				properties.getSecret());

			return new Auth.Jwt(accessToken, signedUser.getUserCode().getEncodedCode());
		} catch (NullPointerException e) { // @Todo - 추후 orElse() 로직 변경 시 함께 조정해야 함.
			throw new CustomException(ErrorCode.INVALID_USER);
		} catch (WebClientResponseException e) {
			throw new CustomException(ErrorCode.INVALID_TOKEN);
		}
	}

	public KakaoAuthService(JwtTokenProvider jwtTokenProvider, UserRepository userRepository,
		UserMatchingCodeRepository userMatchingCodeRepository, Properties properties) {
		super(jwtTokenProvider, userRepository, userMatchingCodeRepository, properties);
	}
}
