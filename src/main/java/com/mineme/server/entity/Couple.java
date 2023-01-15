package com.mineme.server.entity;

import com.mineme.server.entity.enums.CoupleState;
import com.mineme.server.entity.widget.Widget;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private List<User> users = new ArrayList<User>();

    @Builder.Default
    @OneToMany(mappedBy = "widgetId")
    private List<Widget> widgets = new ArrayList<Widget>();

    @Column(name = "NAME")
    private String name;

    @Column(name = "COUPLE_STATE")
    private CoupleState coupleState;

    @Column(name = "BEGIN_DATE")
    private LocalDate beginDate;
}
