package com.mineme.server.entity.widget;

import lombok.Getter;

import javax.persistence.*;


@Getter
@Entity
@Table(name = "WIDGET_TODOLIST")
public class WidgetTodoListEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TODO_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WIDGET_ID")
    private WidgetTodoList widgetId;

    @Column(name = "TODO_CONTENT", columnDefinition = "TEXT")
    private String todoContent;
}
