package com.mineme.server.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Photo extends BaseEntity{

    @Id
    @Column(name = "PHOTO_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PHOTO_URL")
    @NotNull
    private String photoUrl;

    public Photo(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
