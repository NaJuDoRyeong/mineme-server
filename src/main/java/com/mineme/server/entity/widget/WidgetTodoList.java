package com.mineme.server.entity.widget;


import com.mineme.server.entity.enums.WidgetType;
import com.mineme.server.entity.enums.WidgetXPos;
import com.mineme.server.entity.enums.WidgetYPos;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
@DiscriminatorValue("TODO_LIST")
@PrimaryKeyJoinColumn(name = "WIDGET_ID")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WidgetTodoList extends Widget{

    @OneToMany(mappedBy = "widgetId")
    private List<WidgetTodoListEntity> todoListId = new ArrayList<>();

    public WidgetTodoList(LocalDateTime createdAt, LocalDateTime modifiedAt, Long id, WidgetType widgetType, WidgetXPos width, WidgetYPos height, String widgetColor, Character hasTitle, String widgetTitle, List<WidgetTodoListEntity> todoListId) {
        super(createdAt, modifiedAt, id, widgetType, width, height, widgetColor, hasTitle, widgetTitle);
        this.todoListId = todoListId;
    }
}
