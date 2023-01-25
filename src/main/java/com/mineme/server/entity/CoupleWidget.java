package com.mineme.server.entity;

import com.mineme.server.entity.widget.Widget;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "COUPLE_WIDGET")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Column(name = "ORDER")
    private int order;

    public CoupleWidget(LocalDateTime createdAt, LocalDateTime modifiedAt, Long id, Widget widgetId, Couple coupleId, int order) {
        super(createdAt, modifiedAt);
        this.id = id;
        this.widgetId = widgetId;
        this.coupleId = coupleId;
        this.order = order;
    }
}
