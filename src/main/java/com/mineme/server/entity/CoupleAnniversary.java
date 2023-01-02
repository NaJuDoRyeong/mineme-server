package com.mineme.server.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Entity
@RequiredArgsConstructor
@Table(name = "COUPLE_ANNIVERSARY")
public class CoupleAnniversary {

    @Id
    @Column(name = "COUPLE_ANNIVERSARY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;

    @Column(name = "ANNIVERSARY_TYPE")
    @Size(max = 16)
    private String anniversaryType;

    @Column(name = "NAME")
    private String name;
}
