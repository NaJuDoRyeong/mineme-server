package com.mineme.server.entity.widget;


import com.mineme.server.entity.BaseEntity;
import com.mineme.server.entity.enums.WidgetType;
import com.mineme.server.entity.enums.WidgetXPos;
import com.mineme.server.entity.enums.WidgetYPos;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Widget extends BaseEntity {

    @Id
    @Column(name = "WIDGET_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "WIDGET_TYPE")
    @Enumerated(EnumType.STRING)
    @NotNull
    private WidgetType widgetType;

    @Column
    @Enumerated(EnumType.STRING)
    @NotNull
    private WidgetXPos width;

    @Column
    @Enumerated(EnumType.STRING)
    @NotNull
    private WidgetYPos height;

    @Column(name = "WIDGET_COLOR")
    @NotNull
    private String widgetColor;

    @Column(name = "HAS_TITLE")
    @Size(max = 1)
    @NotNull
    private Character hasTitle;

    @Column(name = "WIDGET_TITLE")
    private String widgetTitle;

    protected Widget(WidgetType widgetType,
                  WidgetXPos width,
                  WidgetYPos height,
                  String widgetColor,
                  Character hasTitle,
                  String widgetTitle
    ) {
        this.widgetType = widgetType;
        this.width = width;
        this.height = height;
        this.widgetColor = widgetColor;
        this.hasTitle = hasTitle;
        this.widgetTitle = widgetTitle;
    }
}
