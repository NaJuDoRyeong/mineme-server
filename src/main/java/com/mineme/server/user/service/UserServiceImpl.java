package com.mineme.server.user.service;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mineme.server.common.enums.ErrorCode;
import com.mineme.server.common.exception.CustomException;
import com.mineme.server.entity.Couple;
import com.mineme.server.entity.User;
import com.mineme.server.entity.UserMatchingCode;
import com.mineme.server.entity.enums.UserState;
import com.mineme.server.security.provider.JwtTokenProvider;
import com.mineme.server.user.dto.UserInfos;
import com.mineme.server.user.repository.UserMatchingCodeRepository;
import com.mineme.server.user.repository.UserRepository;
import com.mineme.server.user.util.UserUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final JwtTokenProvider jwtTokenProvider;
	private final UserRepository userRepository;
	private final UserMatchingCodeRepository userMatchingCodeRepository;

	/**
	 * 유저 탈퇴, 커플 상태를 비활성화.
	 */
	@Override
	@Transactional
	public void removeUser() {
		User user = getCurrentUser();

		if (user.getCoupleId() != null) {
			Couple couple = user.getCoupleId();
			try {
				couple.deactivateCouple(user);
			} catch (NullPointerException e) {
				throw new CustomException(ErrorCode.INVALID_USER);
			}
		}

		userRepository.delete(user);
	}

	/**
	 * 회원가입하는 유저의 최초 정보를 입력함.
	 */
	@Override
	public void addUserDetails(UserInfos.Init dto) {
		User currentUser = getCurrentUser();
		currentUser = UserInfos.Init.getInitializedUser(currentUser, dto);

		userRepository.save(currentUser);
	}

	/**
	 * 조회 대상 유저의 유저(커플)매칭코드를 가져옴.
	 * @return 커플매칭코드
	 */
	@Override
	public String getUserMatchingCode(User user) {
		Optional<UserMatchingCode> tmpCode = userMatchingCodeRepository.findByUserId(user);

		if (!tmpCode.isPresent()) {
			UserMatchingCode newCode = userMatchingCodeRepository.save(new UserMatchingCode(user));
			return UserUtil.createUserCode(newCode.getId());
		}

		return UserUtil.createUserCode(tmpCode.get().getId());
	}

	/**
	 * 유저 알림 설정을 변경.
	 * @return UserInfos.Notice
	 */
	@Override
	public UserInfos.Notice modifyUserNotice(UserInfos.Notice notice) {
		try {
			User currentUser = getCurrentUser();
			currentUser.updateUserNoticeState(notice);
			currentUser = userRepository.save(currentUser);

			return new UserInfos.Notice(notice.getType(), currentUser);
		} catch (NullPointerException e) {
			throw new CustomException(ErrorCode.NULL_REQUEST);
		}
	}

	/**
	 * 토큰 인가 기반으로 현재 접속 유저의 username을 가져옴.
	 * @return 현재 액세스한 사용자의 username
	 */
	@Override
	public User getCurrentUser() {
		String username = jwtTokenProvider.getUsername();
		return userRepository.findByUsername(username).orElseThrow(() -> new CustomException(ErrorCode.INVALID_USER));
	}

	/**
	 * 현재 접속 유저의 상태를 확인함.
	 */
	@Override
	public void isValidCurrentUserState(UserState userState) {
		if (getCurrentUser().getUserState() != userState)
			throw new CustomException(ErrorCode.INVALID_USER);
	}

	/**
	 * jwt 로부터 현재 커플 매칭 완료 상태, 정상적으로 모든 서비스를 이용할 수 있는 유저를 조회하는 메소드
	 * @return 현재 접속한 jwt 기반 ACTIVATED 상태인 USER
	 */
	@Override
	public User getCurrentActivatedUser() {
		User user = userRepository.findByUsername(jwtTokenProvider.getUsername())
			.orElseThrow(() -> new CustomException(ErrorCode.INVALID_USER));

		if (user.getUserState() != UserState.ACTIVATED) {
			throw new CustomException(ErrorCode.INVALID_USER_STATE);
		}

		return user;
	}
}
