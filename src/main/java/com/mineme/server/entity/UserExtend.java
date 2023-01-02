package com.mineme.server.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Data
@Entity
@RequiredArgsConstructor
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
}
