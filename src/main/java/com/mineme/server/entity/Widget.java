package com.mineme.server.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Entity
@RequiredArgsConstructor
public class Widget {

    @Id
    @Column(name = "WIDGET_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;

    @Column(name = "WIDGET_TYPE")
    @Size(max = 16)
    private String widgetType;

    /* short or int */
    @Column(name = "WIDTH")
    private short width;

    @Column(name = "HEIGHT")
    private short height;
}
