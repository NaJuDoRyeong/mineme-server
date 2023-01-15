package com.mineme.server.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public abstract class BaseEntity {
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Setter
    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;
}
