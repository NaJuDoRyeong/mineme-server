package com.mineme.server.entity;

import com.mineme.server.entity.widget.Widget;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "COUPLE_WIDGET")
public class CoupleWidget extends BaseEntity{

    @Id
    @Column(name = "COUPLE_WIDGET_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "WIDGET_ID")
    private Widget widgetId;

    @ManyToOne
    @JoinColumn(name = "COUPLE_ID")
    private Couple coupleId;

    @Column(name = "POSITION")
    private String position;
}
