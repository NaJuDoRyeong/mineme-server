package com.mineme.server.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Terms extends BaseEntity{

    @Id
    @Column(name = "TERM_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TERM_TITLE")
    private String termTitle;

    @Column(name = "TERM_DETAIL", columnDefinition = "TEXT")
    private String termDetail;

    @Column(name = "IS_REQUIRED")
    @Size(max = 1)
    private Character isRequired;

    @Column(name = "VERSION")
    @Size(max = 8)
    private String version;

    public Terms(String termTitle, String termDetail, Character isRequired, String version) {
        this.termTitle = termTitle;
        this.termDetail = termDetail;
        this.isRequired = isRequired;
        this.version = version;
    }
}
