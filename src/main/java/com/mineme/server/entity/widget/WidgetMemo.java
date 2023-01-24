package com.mineme.server.entity.widget;


import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;


@Getter
@Entity
@DiscriminatorValue("TODO_DEFAULT")
@PrimaryKeyJoinColumn(name = "WIDGET_ID")
public class WidgetMemo extends Widget {
    private WidgetTodoItem widgetMemo;
}
