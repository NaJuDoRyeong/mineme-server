package com.mineme.server.entity.enums;

import com.mineme.server.common.enums.ErrorCode;
import com.mineme.server.common.exception.CustomException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Provider {
    KAKAO("KAKAO"), APPLE("APPLE");

    private String provider;

    public static Provider of(String code) {
        return Arrays.stream(Provider.values()).filter(r -> r.getProvider().equals(code)).findAny().orElseThrow(() -> new CustomException(ErrorCode.INVALID_PROVIDER));
    }
}
