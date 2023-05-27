package com.mineme.server.user.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mineme.server.common.enums.ErrorCode;
import com.mineme.server.common.exception.CustomException;
import com.mineme.server.entity.Couple;
import com.mineme.server.entity.User;
import com.mineme.server.entity.enums.UserState;
import com.mineme.server.security.provider.JwtTokenProvider;
import com.mineme.server.user.dto.UserInfos;
import com.mineme.server.user.repository.CoupleRepository;
import com.mineme.server.user.repository.UserMatchingCodeRepository;
import com.mineme.server.user.repository.UserRepository;
import com.mineme.server.user.util.UserUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CoupleServiceImpl implements UserService {

	private final JwtTokenProvider jwtTokenProvider;
	private final UserRepository userRepository;
	private final UserMatchingCodeRepository userMatchingCodeRepository;
	private final CoupleRepository coupleRepository;

	/**
	 * 유저 코드를 기반으로 커플을 연결함.
	 * 두 커플의 상태가 DEACTIVATED(서로 연결할 수 있는 상태)일 경우 연결 수행
	 * @Todo 추후, 별도의 검증 과정이 필요함.
	 */
	@Transactional
	public void addUserRelationByCouple(String userCode) {
		User me = getCurrentUser();
		Long mineRawCode = Long.parseLong(UserUtil.decodeCodeFromBase62(userCode));
		User mine = userMatchingCodeRepository.findById(mineRawCode)
			.orElseThrow(() -> new CustomException(ErrorCode.INVALID_USER))
			.getUserId();

		if (me.getUserState() == UserState.DEACTIVATED && mine.getUserState() == UserState.DEACTIVATED) {
			Couple couple = Couple.getEmptyCoupleEntity(me.getNickname(), mine.getNickname());
			couple = coupleRepository.save(couple);

			me.matchCouple(couple);
			mine.matchCouple(couple);
		} else {
			throw new CustomException(ErrorCode.INVALID_COUPLE);
		}

		/* @Todo 커플매칭코드를 초기화 하는 로직 추가 */
	}

	/**
	 * 커플 프로필을 변경.
	 * @return UserInfos.Modifying
	 */
	@Transactional
	public UserInfos.Modifying modifyCoupleProfile(UserInfos.Modifying dto) {
		try {
			User currentUser = getCurrentActivatedUser();

			Couple updatedCouple = currentUser.updateUserProfile(Optional.ofNullable(dto));
			userRepository.save(currentUser);
			coupleRepository.save(updatedCouple);

			return new UserInfos.Modifying(currentUser);
		} catch (NullPointerException e) {
			throw new CustomException(ErrorCode.INVALID_COUPLE_PROFILE);
		}
	}

	@Override
	public User getCurrentUser() {
		String username = jwtTokenProvider.getUsername();
		return userRepository.findByUsername(username).orElseThrow(() -> new CustomException(ErrorCode.INVALID_USER));
	}

	@Override
	public User getCurrentActivatedUser() {
		User user = userRepository.findByUsername(jwtTokenProvider.getUsername())
			.orElseThrow(() -> new CustomException(ErrorCode.INVALID_USER));

		if (user.getUserState() != UserState.ACTIVATED)
			throw new CustomException(ErrorCode.INVALID_USER_STATE);

		return user;
	}

	@Override
	public void isValidCurrentUserState(UserState userState) {

	}
}
