package com.wanted.teamr.tastyfinder.api.matzip.domain;


import com.wanted.teamr.tastyfinder.datapipelining.domain.MatzipRaw;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

import com.wanted.teamr.tastyfinder.api.review.domain.Review;
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
    @JoinColumn(name = "matzip_raw_id")
    MatzipRaw matzipRaw;

    @Column(nullable = false)
    Long totalRating;

    @Column(nullable = false)
    Long reviewCount;
  
    @OneToMany(mappedBy = "matzip", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Review> reviews = new ArrayList<>();

    public void updateTotalRating(Review review) {
        totalRating += review.getRating();
    }

    public void updateReviewCount() {
        reviewCount += 1;
    }

    /**
     * 현재 맛집과의 거리 계산 후 반환
     * @param requestLocation 해당 맛집과 거리 비교할 위치
     * @return 거리
     */
    public double calcDistanceFrom(Location requestLocation) {
        return matzipRaw.getLocation().distanceFrom(requestLocation);
    } 
  
    @Builder
    public Matzip(Long totalRating, Long reviewCount, List<Review> reviews) {
        this.totalRating = totalRating;
        this.reviewCount = reviewCount;
        this.reviews = reviews;
    }

}
