package com.wanted.teamr.tastyfinder.api.matzip.dto;

import com.wanted.teamr.tastyfinder.api.matzip.domain.Location;
import com.wanted.teamr.tastyfinder.api.matzip.domain.Matzip;
import lombok.Builder;
import lombok.Getter;

// TODO: 반환할 필드 더 추가

/**
 * 맛집 목록 조회는 상세 조회 대비 요약된 형태로 반환
 */
@Getter
public class MatzipSummaryResponse {

    private String name;

    private double distance;

    private double avgRating;

    @Builder
    private MatzipSummaryResponse(String name, double distance, double avgRating) {
        this.name = name;
        this.distance = distance;
        this.avgRating = avgRating;
    }


    public static MatzipSummaryResponse of(Matzip matzip, Location requestLocation) {
        return MatzipSummaryResponse.builder()
                                    .name(matzip.getMatzipRaw().getBizplcNm())
                                    .distance(matzip.calcDistanceFrom(requestLocation))
                                    .avgRating((double) matzip.getTotalRating() / matzip.getReviewCount())
                                    .build();
    }
}

