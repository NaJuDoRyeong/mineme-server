package com.mineme.server.entity.history;

import com.mineme.server.entity.Couple;
import com.mineme.server.entity.CoupleAnniversary;
import com.mineme.server.entity.User;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "USER_COUPLE_HISTORY")
public class UserCoupleHistory {

    @Id
    @Column(name = "USER_COUPLE_HISTORY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Couple coupleId;

    @ManyToOne
    @JoinColumn
    private User userId;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "OPERATION")
    private String operation;
}
