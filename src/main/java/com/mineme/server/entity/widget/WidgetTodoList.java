package com.mineme.server.entity.widget;


import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
@DiscriminatorValue("TODO_LIST")
@PrimaryKeyJoinColumn(name = "WIDGET_ID")
public class WidgetTodoList extends Widget{

    @OneToMany(mappedBy = "widgetId")
    private List<WidgetTodoListEntity> todoListId = new ArrayList<>();
}
