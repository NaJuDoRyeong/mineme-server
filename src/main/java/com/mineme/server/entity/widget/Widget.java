package com.mineme.server.entity.widget;


import com.mineme.server.entity.BaseEntity;
import com.mineme.server.entity.CoupleWidget;
import com.mineme.server.entity.enums.WidgetType;
import com.mineme.server.entity.enums.WidgetXPos;
import com.mineme.server.entity.enums.WidgetYPos;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Widget extends BaseEntity {

    @Id
    @Column(name = "WIDGET_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "widgetId")
    private List<CoupleWidget> coupleWidgetId = new ArrayList<>();

    @Column(name = "WIDGET_TYPE")
    @Enumerated(EnumType.STRING)
    private WidgetType widgetType;

    @Column
    @Enumerated(EnumType.STRING)
    private WidgetXPos width;

    @Column
    @Enumerated(EnumType.STRING)
    private WidgetYPos height;

    @Column(name = "WIDGET_COLOR")
    private String widgetColor;

    @Column(name = "HAS_TITLE")
    private Character hasTitle;

    @Column(name = "WIDGET_TITLE")
    private String widgetTitle;
}
