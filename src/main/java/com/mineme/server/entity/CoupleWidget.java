package com.mineme.server.entity;

import com.mineme.server.entity.widget.Widget;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
    @NotNull
    private int order;

    public CoupleWidget(Widget widgetId, Couple coupleId, int order) {
        this.widgetId = widgetId;
        this.coupleId = coupleId;
        this.order = order;
    }
}
