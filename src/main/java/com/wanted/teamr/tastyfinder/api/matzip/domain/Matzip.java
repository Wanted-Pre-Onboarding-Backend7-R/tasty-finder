package com.wanted.teamr.tastyfinder.api.matzip.domain;

import com.wanted.teamr.tastyfinder.api.review.domain.Review;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Matzip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false)
    private int totalRating = 0;

    @Column(nullable = false)
    private int reviewCount = 0;

    @OneToMany(mappedBy = "matzip", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Review> reviews = new ArrayList<>();

    public void updateTotalRating(Review review) {
        totalRating += review.getRating();
    }

    public void updateReviewCount() {
        reviewCount += 1;
    }

    @Builder
    public Matzip(int totalRating, int reviewCount, List<Review> reviews) {
        this.totalRating = totalRating;
        this.reviewCount = reviewCount;
        this.reviews = reviews;
    }

}
