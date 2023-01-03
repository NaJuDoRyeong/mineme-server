package com.mineme.server.entity.widget;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Widget {

    @Id
    @Column(name = "WIDGET_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @OneToMany(mappedBy = "widgetId")
    private List<Widget> widgets = new ArrayList<Widget>();

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;

    @Column(name = "WIDGET_TYPE")
    @Size(max = 16)
    private String widgetType;

    @Column(name = "HAS_TITLE")
    private Character hasTitle;

    @Column(name = "WIDGET_TITLE")
    private String widgetTitle;

    @Column(name = "WIDGET_COLOR")
    private String widgetColor;

    @Column
    private int width;

    @Column
    private int height;
}
