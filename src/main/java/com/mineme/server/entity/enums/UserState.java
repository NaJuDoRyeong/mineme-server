package com.mineme.server.entity.enums;

import lombok.Getter;

@Getter
public enum UserState {
    ACTIVATED,
    DEACTIVATED,
    DELETED,
    PENDING,
    UNAUTHORIZED
}
