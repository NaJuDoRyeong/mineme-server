package com.mineme.server.entity.enums;

import lombok.Getter;

@Getter
public enum UserState {
    ACTIVATED,      // 활성화 - 커플 연결된 상태
    DEACTIVATED,    // 비활성화 - 커플 연결이 되지 않은 상태
    DELETED,        // 탈퇴 - 유저가 탈퇴를 선택한 상태
    PENDING,        // 대기 - 가입 후 커플 연결 대기 중인 상태
    UNAUTHORIZED    // 비인가 - 부적절하게 인가가 해제된 상태. 재인증 필요.
}
