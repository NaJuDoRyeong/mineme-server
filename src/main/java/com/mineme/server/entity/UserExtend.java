package com.mineme.server.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "USER_EXTEND")
public class UserExtend {

    @Id
    @JoinColumn(name = "USER_ID")
    @OneToOne(fetch = FetchType.LAZY)
    private User id;

    @Column(name = "PHONE_NUMBER")
    @Size(max = 16)
    private String phoneNumber;

    @Column(name = "EXTRA_VALUES")
    private String extraValues;

    @Builder
    public UserExtend(String phoneNumber, String extraValues) {
        this.phoneNumber = phoneNumber;
        this.extraValues = extraValues;
    }
}
