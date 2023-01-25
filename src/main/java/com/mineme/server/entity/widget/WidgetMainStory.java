package com.mineme.server.entity.widget;

import com.mineme.server.entity.CoupleWidget;
import com.mineme.server.entity.Photo;
import com.mineme.server.entity.Post;
import com.mineme.server.entity.enums.WidgetType;
import com.mineme.server.entity.enums.WidgetXPos;
import com.mineme.server.entity.enums.WidgetYPos;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Entity
@DiscriminatorValue("MAIN_STORY")
@PrimaryKeyJoinColumn(name = "WIDGET_ID")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WidgetMainStory extends Widget{

    /* Post ID */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MAIN_STORY_ID")
    private Post mainStoryId;

    /* Photo Id*/
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MAIN_PHOTO_ID")
    private Photo mainPhotoId;

    private LocalDate mainStoryDate;
    private String dest;

    public WidgetMainStory(LocalDateTime createdAt, LocalDateTime modifiedAt, Long id, WidgetType widgetType, WidgetXPos width, WidgetYPos height, String widgetColor, Character hasTitle, String widgetTitle, Post mainStoryId, Photo mainPhotoId, LocalDate mainStoryDate, String dest) {
        super(createdAt, modifiedAt, id, widgetType, width, height, widgetColor, hasTitle, widgetTitle);
        this.mainStoryId = mainStoryId;
        this.mainPhotoId = mainPhotoId;
        this.mainStoryDate = mainStoryDate;
        this.dest = dest;
    }
}
