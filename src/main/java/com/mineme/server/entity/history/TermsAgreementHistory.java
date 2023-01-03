package com.mineme.server.entity.history;

import com.mineme.server.entity.Couple;
import com.mineme.server.entity.User;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "TERMS_AGREEMENT_HISTORY")
public class TermsAgreementHistory {

    @Id
    @Column(name = "TERMS_AGREEMENT_HISTORY_ID")
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

    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;

    @Column(name = "IS_AGREE")
    private Character isAgree;
}
