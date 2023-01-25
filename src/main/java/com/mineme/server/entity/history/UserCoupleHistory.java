package com.mineme.server.entity.history;

import com.mineme.server.entity.Couple;
import com.mineme.server.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "USER_COUPLE_HISTORY")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserCoupleHistory {

    @Id
    @Column(name = "USER_COUPLE_HISTORY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "COUPLE_ID")
    private Couple coupleId;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User userId;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "OPERATION")
    private String operation;

    public UserCoupleHistory(Long id, Couple coupleId, User userId, LocalDateTime createdAt, String operation) {
        this.id = id;
        this.coupleId = coupleId;
        this.userId = userId;
        this.createdAt = createdAt;
        this.operation = operation;
    }
}
