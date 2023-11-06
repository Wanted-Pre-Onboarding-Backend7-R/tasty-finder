package com.wanted.teamr.tastyfinder.api.matzip.domain;


import com.wanted.teamr.tastyfinder.api.review.domain.Review;
import com.wanted.teamr.tastyfinder.api.review.dto.ReviewRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Matzip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "temp_matzip_raw_id")
    TempMatzipRaw tempMatzipRaw;

    @Column(nullable = false)
    Long totalRating;

    @Column(nullable = false)
    Long reviewCount;
  
    @OneToMany(mappedBy = "matzip", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Review> reviews = new ArrayList<>();

    public void updateTotalRating(ReviewRequest request) {
        totalRating += request.getRating();
    }

    public void calculateTotalRating(Review review, ReviewRequest request) {
        Long currentRating = review.getRating();
        Long updatedRating = request.getRating();
        if (currentRating > updatedRating) {
            decreaseTotalRating(Math.abs(currentRating - updatedRating));
        } else {
            increaseTotalRating(Math.abs(updatedRating - currentRating));
        }
    }

    public void increaseTotalRating(Long ratingValue) {
        totalRating += ratingValue;
    }

    public void decreaseTotalRating(Long ratingValue) {
        totalRating -= ratingValue;
    }

    public void decreaseTotalRating(Review review) {
        totalRating -= review.getRating();
    }

    public void increaseReviewCount() {
        reviewCount += 1;
    }

    public void decreaseReviewCount() {
        reviewCount -= 1;
    }

    /**
     * 현재 맛집과의 거리 계산 후 반환
     * @param requestLocation 해당 맛집과 거리 비교할 위치
     * @return 거리
     */
    public double calcDistanceFrom(Location requestLocation) {
        return tempMatzipRaw.location.distanceFrom(requestLocation);
    } 
  
    @Builder
    public Matzip(Long totalRating, Long reviewCount, List<Review> reviews) {
        this.totalRating = totalRating;
        this.reviewCount = reviewCount;
        this.reviews = reviews;
    }

}
