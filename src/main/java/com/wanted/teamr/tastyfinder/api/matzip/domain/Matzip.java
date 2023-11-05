package com.wanted.teamr.tastyfinder.api.matzip.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Matzip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "temp_matzip_row_id")
    TempMatzipRow tempMatzipRow;

    @Column(nullable = false)
    Long totalRating;

    @Column(nullable = false)
    Long reviewCount;

    @Builder
    private Matzip(Long totalRating, Long reviewCount) {
        this.totalRating = totalRating;
        this.reviewCount = reviewCount;
    }

    /**
     * 현재 맛집과의 거리 계산 후 반환
     * @param requestLocation 해당 맛집과 거리 비교할 위치
     * @return 거리
     */
    public double calcDistanceFrom(Location requestLocation) {
        return tempMatzipRow.location.distanceFrom(requestLocation);
    }

}

