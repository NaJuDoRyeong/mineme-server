package com.mineme.server.entity.widget;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;

import com.mineme.server.entity.enums.FontStyleDday;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@DiscriminatorValue("DDAY")
@PrimaryKeyJoinColumn(name = "WIDGET_ID")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WidgetDday extends Widget{

    @Size(max = 1)
    private Character hasBaseDate;
    private LocalDate baseDate;
    private int dday;
    @Enumerated(EnumType.STRING)
    private FontStyleDday fontStyle;
    private String ddayBg;

    public WidgetDday(Character hasBaseDate, LocalDate baseDate, int dday, FontStyleDday fontStyle, String ddayBg) {
        this.hasBaseDate = hasBaseDate;
        this.baseDate = baseDate;
        this.dday = dday;
        this.fontStyle = fontStyle;
        this.ddayBg = ddayBg;
    }
}
