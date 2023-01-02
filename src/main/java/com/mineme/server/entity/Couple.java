package com.mineme.server.entity;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@RequiredArgsConstructor
public class Couple {

    @Id
    @Column(name = "COUPLE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @OneToMany(mappedBy = "id")
    private List<User> users = new ArrayList<User>();

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;

    @Column(name = "NAME")
    private String name;

    @Column(name = "COUPLE_STATE")
    private String coupleState;

    @Column(name = "BEGIN_DATE")
    private LocalDate beginDate;
}
