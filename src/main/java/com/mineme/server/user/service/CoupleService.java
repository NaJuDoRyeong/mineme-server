package com.mineme.server.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mineme.server.auth.service.AuthService;
import com.mineme.server.common.enums.ErrorCode;
import com.mineme.server.common.exception.CustomException;
import com.mineme.server.entity.Couple;
import com.mineme.server.entity.User;
import com.mineme.server.entity.enums.UserState;
import com.mineme.server.user.repository.CoupleRepository;
import com.mineme.server.user.repository.UserMatchingCodeRepository;
import com.mineme.server.user.util.UserUtil;

@Service
public class CoupleService implements AuthService {

	private final UserMatchingCodeRepository userMatchingCodeRepository;
	private final CoupleRepository coupleRepository;

	public CoupleService(UserMatchingCodeRepository userMatchingCodeRepository, CoupleRepository coupleRepository) {
		this.userMatchingCodeRepository = userMatchingCodeRepository;
		this.coupleRepository = coupleRepository;
	}

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

	@Override
	public User getCurrentUser() {
		return null;
	}

	@Override
	public void isValidCurrentUserState(UserState userState) {

	}
}
