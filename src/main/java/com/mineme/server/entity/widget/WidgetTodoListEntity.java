package com.mineme.server.entity.widget;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@Entity
@Table(name = "WIDGET_TODOLIST")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public WidgetTodoListEntity(Long id, WidgetTodoList widgetId, String todoContent) {
        this.id = id;
        this.widgetId = widgetId;
        this.todoContent = todoContent;
    }
}
