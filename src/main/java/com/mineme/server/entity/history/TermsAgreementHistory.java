package com.mineme.server.entity.history;


import com.mineme.server.entity.Terms;
import com.mineme.server.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "TERMS_AGREEMENT_HISTORY")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TermsAgreementHistory {

    @Id
    @Column(name = "TERMS_AGREEMENT_HISTORY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "TERM_ID")
    private Terms termId;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User userId;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;

    @Column(name = "IS_AGREE")
    @Size(max = 1)
    private Character isAgree;

    public TermsAgreementHistory(Long id, Terms termId, User userId, LocalDateTime createdAt, LocalDateTime modifiedAt, Character isAgree) {
        this.id = id;
        this.termId = termId;
        this.userId = userId;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.isAgree = isAgree;
    }
}
