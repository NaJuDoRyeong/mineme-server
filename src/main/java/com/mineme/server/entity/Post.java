package com.mineme.server.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity{

    @Id
    @Column(name = "POST_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "DATED_AT")
    private LocalDateTime datedAt;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENT", columnDefinition = "TEXT")
    private String content;

    public Post(LocalDateTime createdAt, LocalDateTime modifiedAt, Long id, LocalDateTime datedAt, String title, String content) {
        super(createdAt, modifiedAt);
        this.id = id;
        this.datedAt = datedAt;
        this.title = title;
        this.content = content;
    }
}
