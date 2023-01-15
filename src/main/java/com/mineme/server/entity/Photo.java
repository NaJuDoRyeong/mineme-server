package com.mineme.server.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@RequiredArgsConstructor
public class Photo extends BaseEntity{

    @Id
    @Column(name = "PHOTO_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PHOTO_URL")
    private String photoUrl;
}
