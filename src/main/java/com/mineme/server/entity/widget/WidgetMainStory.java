package com.mineme.server.entity.widget;

import com.mineme.server.entity.Photo;
import com.mineme.server.entity.Post;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;


@Getter
@Entity
@DiscriminatorValue("MAIN_STORY")
@PrimaryKeyJoinColumn(name = "WIDGET_ID")
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
}
