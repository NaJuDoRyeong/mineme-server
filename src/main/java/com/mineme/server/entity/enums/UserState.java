package com.mineme.server.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserState {
    Activated,
    Deactivated,
    Deleted,
    Pending,
    Unauthoriazed;
}
