package com.mineme.server.entity.widget;

import javax.persistence.*;
import java.time.LocalDate;

import lombok.Getter;

@Getter
@Entity
@DiscriminatorValue("DDAY")
@PrimaryKeyJoinColumn(name = "WIDGET_ID")
public class WidgetDday extends Widget{

    private Character hasBaseDate;
    private LocalDate baseDate;
    private int dday;

    private Character Style;
}
