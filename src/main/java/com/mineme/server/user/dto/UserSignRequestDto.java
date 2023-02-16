package com.mineme.server.user.dto;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSignRequestDto {
    private String accessToken;
    private String providerType;
    private String username;
}
