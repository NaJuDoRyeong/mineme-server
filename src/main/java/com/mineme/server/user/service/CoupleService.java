package com.mineme.server.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mineme.server.auth.dto.Auth;
import com.mineme.server.auth.service.AuthService;
import com.mineme.server.common.enums.ErrorCode;
import com.mineme.server.common.exception.CustomException;
import com.mineme.server.entity.Couple;
import com.mineme.server.entity.User;
import com.mineme.server.security.config.Properties;
import com.mineme.server.security.provider.JwtTokenProvider;
import com.mineme.server.user.repository.CoupleRepository;
import com.mineme.server.user.repository.UserMatchingCodeRepository;
import com.mineme.server.user.repository.UserRepository;
import com.mineme.server.user.util.UserUtil;

@Service
public class CoupleService extends AuthService<Object> {

	private final UserMatchingCodeRepository userMatchingCodeRepository;
	private final CoupleRepository coupleRepository;

	public CoupleService(JwtTokenProvider jwtTokenProvider, UserRepository userRepository, Properties properties,
		UserMatchingCodeRepository userMatchingCodeRepository, CoupleRepository coupleRepository) {
		super(jwtTokenProvider, userRepository, properties);
		this.userMatchingCodeRepository = userMatchingCodeRepository;
		this.coupleRepository = coupleRepository;
	}

	@Transactional
	public void addCouple(String userCode) {
		User me = getCurrentUser();
		Long mineRawCode = Long.parseLong(UserUtil.decodeCodeFromBase62(userCode));
		User mine = userMatchingCodeRepository.findById(mineRawCode)
			.orElseThrow(() -> new CustomException(ErrorCode.INVALID_USER))
			.getUserId();

		Couple couple = Couple.getEmptyCoupleEntity(me.getNickname() + "와 " + mine.getNickname());
		couple = coupleRepository.save(couple);

		me.matchCouple(couple);
		mine.matchCouple(couple);

		/* @Todo 커플매칭코드를 초기화 하는 로직 추가 */
	}

	@Override
	public Auth.CreatedJwt getUserDetails(Object dto) {
		return null;
	}
}
