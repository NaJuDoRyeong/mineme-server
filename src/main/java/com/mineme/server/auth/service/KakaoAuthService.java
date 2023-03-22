package com.mineme.server.auth.service;

import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.mineme.server.auth.dto.Auth;
import com.mineme.server.auth.dto.Kakao;
import com.mineme.server.auth.utils.AuthClientUtil;
import com.mineme.server.common.enums.ErrorCode;
import com.mineme.server.common.exception.CustomException;
import com.mineme.server.entity.User;
import com.mineme.server.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KakaoAuthService extends AuthService<Auth.SignRequest> {

	private final UserService userService;

	@Transactional
	@Override
	public Auth.Jwt getUserDetails(Auth.SignRequest dto) {
		try {
			Kakao.User user = AuthClientUtil.getKakaoUser(dto).block();
			User signedUser = userRepository.findByUsername(user.getId()).orElse(null);

			if (signedUser == null) {
				User pendingUser = User.toPendingUserEntity(user.getId(), dto);
				// pendingUser.setUserCode(getUserMatchingCode());
				signedUser = userRepository.save(pendingUser);
			}

			String accessToken = jwtTokenProvider.create(signedUser.getUsername(), signedUser.getUserState(),
				properties.getSecret());

			return new Auth.Jwt(accessToken, userService.getUserMatchingCode(signedUser));
		} catch (NullPointerException e) { // @Todo - 추후 orElse() 로직 변경 시 함께 조정해야 함.
			throw new CustomException(ErrorCode.INVALID_USER);
		} catch (WebClientResponseException e) {
			throw new CustomException(ErrorCode.INVALID_TOKEN);
		} catch (NoSuchAlgorithmException e) {
			throw new CustomException(ErrorCode.INVALID_TOKEN);
		}
	}
}
