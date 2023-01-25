package com.mineme.server.entity.widget;


import com.mineme.server.entity.enums.WidgetType;
import com.mineme.server.entity.enums.WidgetXPos;
import com.mineme.server.entity.enums.WidgetYPos;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


@Getter
@Entity
@DiscriminatorValue("TODO_DEFAULT")
@PrimaryKeyJoinColumn(name = "WIDGET_ID")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WidgetMemo extends Widget {
    private String name;

    @Size(max = 1)
    private Character isDone;

    public WidgetMemo(LocalDateTime createdAt, LocalDateTime modifiedAt, Long id, WidgetType widgetType, WidgetXPos width, WidgetYPos height, String widgetColor, Character hasTitle, String widgetTitle, String name, Character isDone) {
        super(createdAt, modifiedAt, id, widgetType, width, height, widgetColor, hasTitle, widgetTitle);
        this.name = name;
        this.isDone = isDone;
    }
}
