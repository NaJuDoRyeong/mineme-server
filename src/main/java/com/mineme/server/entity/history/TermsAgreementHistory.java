package com.mineme.server.entity.history;


import com.mineme.server.entity.BaseEntity;
import com.mineme.server.entity.Terms;
import com.mineme.server.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Entity
@Table(name = "TERMS_AGREEMENT_HISTORY")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TermsAgreementHistory extends BaseEntity {

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

    @Column(name = "IS_AGREE")
    @Size(max = 1)
    @NotNull
    private Character isAgree;

    public TermsAgreementHistory(Terms termId, User userId, Character isAgree) {
        this.termId = termId;
        this.userId = userId;
        this.isAgree = isAgree;
    }
}
