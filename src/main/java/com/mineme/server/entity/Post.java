package com.mineme.server.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity{

    @Id
    @Column(name = "POST_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "postId")
    private List<Photo> photoId = new ArrayList<>();

    @Column(name = "DATED_AT")
    private LocalDate datedAt;

    @Column(name = "CONTENT", columnDefinition = "TEXT")
    @NotNull
    private String content;

    public Post(LocalDate datedAt, String content) {
        this.datedAt = datedAt;
        this.content = content;
    }
}
