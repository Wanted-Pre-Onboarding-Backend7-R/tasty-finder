package com.wanted.teamr.tastyfinder.api.matzip.dto;

import com.wanted.teamr.tastyfinder.api.matzip.domain.Matzip;
import lombok.Builder;
import lombok.Getter;

// TODO: 반환할 필드 더 추가

/**
 * 맛집 목록 조회는 상세 조회 대비 요약된 형태로 반환
 */
@Getter
public class MatzipSummaryReponse {

    private String name;

    private double distance;

    private double avgRating;

    @Builder
    private MatzipSummaryReponse(String name, double distance, double avgRating) {
        this.name = name;
        this.distance = distance;
        this.avgRating = avgRating;
    }


    public static MatzipSummaryReponse of(Matzip matzip, double distance) {
        return MatzipSummaryReponse.builder()
                                   .name(matzip.getMatzipRaw().getBizplcNm())
                                   .distance(distance)
                                   .avgRating((double) matzip.getTotalRating() / matzip.getReviewCount())
                                   .build();
    }
}

