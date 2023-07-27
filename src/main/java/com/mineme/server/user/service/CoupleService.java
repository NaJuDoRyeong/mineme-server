package com.mineme.server.user.service;

import com.mineme.server.entity.User;
import com.mineme.server.user.dto.UserInfos;

public interface CoupleService {
	void addUserRelationByCouple(String userCode);

	UserInfos.Modifying modifyCoupleProfile(UserInfos.Modifying dto);

	User getCurrentUser();

	User getCurrentActivatedUser();
}
