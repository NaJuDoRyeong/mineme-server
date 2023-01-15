package com.mineme.server.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "COUPLE_ANNIVERSARY")
public class CoupleAnniversary extends BaseEntity{

    @Id
    @Column(name = "COUPLE_ANNIVERSARY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ANNIVERSARY_TYPE")
    @Size(max = 16)
    private String anniversaryType;

    @Column(name = "NAME")
    private String name;
}
