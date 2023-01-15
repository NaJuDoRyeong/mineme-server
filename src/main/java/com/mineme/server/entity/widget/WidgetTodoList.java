package com.mineme.server.entity.widget;


import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import java.util.HashMap;
import java.util.Map;


@Getter
@Entity
@DiscriminatorValue("TODO_LIST")
@PrimaryKeyJoinColumn(name = "WIDGET_ID")
public class WidgetTodoList extends Widget{

    Map<String, WidgetTodoItem> widgetTodo = new HashMap<String, WidgetTodoItem>();
}
