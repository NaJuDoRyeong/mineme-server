package com.mineme.server.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@RequiredArgsConstructor
public class Post {

    @Id
    @Column(name = "POST_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;

    @Column(name = "DATED_AT")
    private LocalDateTime datedAt;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENT")
    private String content;
}
