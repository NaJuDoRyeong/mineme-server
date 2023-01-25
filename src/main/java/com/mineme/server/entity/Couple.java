package com.mineme.server.entity;

import com.mineme.server.entity.enums.CoupleState;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Couple extends BaseEntity{

    @Id
    @Column(name = "COUPLE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "coupleId")
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "coupleId")
    private List<CoupleWidget> coupleWidgets = new ArrayList<>();

    @Column(name = "NAME")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "COUPLE_STATE")
    private CoupleState coupleState;

    @Column(name = "BEGIN_DATE")
    private LocalDate beginDate;


    @Builder
    public Couple(Long id,
                  List<User> users,
                  List<CoupleWidget> coupleWidgets,
                  String name,
                  CoupleState coupleState,
                  LocalDate beginDate
    ) {
        this.id = id;
        this.users = users;
        this.coupleWidgets = coupleWidgets;
        this.name = name;
        this.coupleState = coupleState;
        this.beginDate = beginDate;
    }
}
