package com.mineme.server.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@RequiredArgsConstructor
public class Photo {

    @Id
    @Column(name = "PHOTO_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;

    @Column(name = "PHOTO_URL")
    private String photoUrl;
}
