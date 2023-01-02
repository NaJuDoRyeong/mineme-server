package com.mineme.server.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Entity
@RequiredArgsConstructor
public class Terms {

    @Id
    @Column(name = "TEAR_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;

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
