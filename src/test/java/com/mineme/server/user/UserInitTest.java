package com.mineme.server.user;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.mineme.server.common.exception.CustomException;
import com.mineme.server.entity.User;
import com.mineme.server.entity.enums.Provider;
import com.mineme.server.entity.enums.UserState;
import com.mineme.server.user.dto.UserInfos;

@RunWith(SpringRunner.class)
@DisplayName("유저 초기 설정 테스트")
public class UserInitTest {

	private final static User testUser = new User(null, null, "123456", "가나다", UserState.PENDING, Provider.APPLE, null,
		null, LocalDateTime.now(), null, null, null, null, null, null, null, null, false, false, false);

	@Test
	@DisplayName("정상적인 유저를 검증함.")
	public void validateUserInitDtoPassTest() {

		//given
		UserInfos.Init init = new UserInfos.Init("가나다", "1998-01-10", "M");

		//when
		User validatedUser = UserInfos.Init.getInitializedUser(testUser, init);

		//then
		Assertions.assertEquals(LocalDate.parse("1998-01-10"), validatedUser.getBirthday());
		Assertions.assertEquals('M', validatedUser.getGender());
	}

	@Test
	@DisplayName("유저의 정상적인 성별을 검증함.")
	public void validateUserInitDtoPass2Test() {

		//given
		UserInfos.Init init = new UserInfos.Init("가나다", "1998-01-10", "MF");

		//when
		User validatedUser = UserInfos.Init.getInitializedUser(testUser, init);

		//then
		Assertions.assertEquals(LocalDate.parse("1998-01-10"), validatedUser.getBirthday());
		Assertions.assertEquals('M', validatedUser.getGender());
	}

	@Test
	@DisplayName("유저의 비정상적인 생년월일을 검증함.")
	public void validateUserInitDtoFailedTest() {

		//given
		UserInfos.Init init = new UserInfos.Init("가나다", "19f98-01-10fwe", "MF");

		//when

		//then
		Assertions.assertThrows(CustomException.class, () -> {
			User validatedUser = UserInfos.Init.getInitializedUser(testUser, init);
		});
	}

	@Test
	@DisplayName("유저의 비정상적인 성별을 검증함.")
	public void validateUserInitDtoFailed2Test() {

		//given
		UserInfos.Init init = new UserInfos.Init("가나다", "1998-01-10", "lAS");

		//when

		//then
		Assertions.assertThrows(CustomException.class, () -> {
			User validatedUser = UserInfos.Init.getInitializedUser(testUser, init);
		});
	}
}
