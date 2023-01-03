package com.mineme.server.entity.widget;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WidgetTodoItem {
    private String name;
    private Character isDone;
}
