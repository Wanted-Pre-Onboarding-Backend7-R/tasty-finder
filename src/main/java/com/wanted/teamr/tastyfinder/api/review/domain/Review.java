package com.wanted.teamr.tastyfinder.api.review.domain;

import com.wanted.teamr.tastyfinder.api.common.BaseTimeEntity;
import com.wanted.teamr.tastyfinder.api.review.dto.ReviewRequest;
import com.wanted.teamr.tastyfinder.api.matzip.domain.Matzip;
import com.wanted.teamr.tastyfinder.api.member.domain.Member;
import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review  extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long rating;

    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Member member;

    @ManyToOne
    @JoinColumn
    private Matzip matzip;

    @Builder
    public Review(Long rating, String content, Member member, Matzip matzip) {
        this.rating = rating;
        this.content = content;
        this.member = member;
        this.matzip = matzip;
    }

    public static Review of(ReviewRequest request, Member member, Matzip matzip) {
        return builder()
                .rating(request.getRating())
                .content(request.getContent())
                .member(member)
                .matzip(matzip).build();
    }

}
