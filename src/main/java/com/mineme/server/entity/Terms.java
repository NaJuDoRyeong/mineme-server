package com.mineme.server.entity;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Entity
public class Terms extends BaseEntity{

    @Id
    @Column(name = "TEAR_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* 약관 제목 타입, 길이 수정 필요 */
    @Column(name = "TERM_TITLE")
    private String termTitle;

    @Column(name = "TERM_DETAIL")
    private String termDetail;

    @Column(name = "IS_REQUIRED")
    private String isRequired;

    @Column(name = "VERSION")
    @Size(max = 8)
    private String version;
}
