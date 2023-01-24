package com.mineme.server.entity;

import com.mineme.server.entity.enums.CoupleState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Couple extends BaseEntity{

    @Id
    @Column(name = "COUPLE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @OneToMany(mappedBy = "coupleId")
    private List<User> users = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "coupleId")
    private List<CoupleWidget> coupleWidgets = new ArrayList<>();

    @Column(name = "NAME")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "COUPLE_STATE")
    private CoupleState coupleState;

    @Column(name = "BEGIN_DATE")
    private LocalDate beginDate;
}
