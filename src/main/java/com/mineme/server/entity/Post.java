package com.mineme.server.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@RequiredArgsConstructor
public class Post extends BaseEntity{

    @Id
    @Column(name = "POST_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "DATED_AT")
    private LocalDateTime datedAt;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENT")
    private String content;
}
