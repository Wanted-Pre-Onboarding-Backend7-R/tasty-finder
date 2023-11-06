package com.wanted.teamr.tastyfinder.api.matzip.dto;

import com.wanted.teamr.tastyfinder.api.matzip.domain.Matzip;
import com.wanted.teamr.tastyfinder.api.review.dto.ReviewResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class MatzipResponse {
    private final Long matzipId;
    private final int avgRating;
    private final List<ReviewResponse> reviewList;

    @Builder
    public MatzipResponse(Long matzipId, int avgRating, List<ReviewResponse> reviewList) {
        this.matzipId = matzipId;
        this.avgRating = avgRating;
        this.reviewList = reviewList;
    }

    public static MatzipResponse of(Matzip matzip, int avgRating, List<ReviewResponse> reviewList) {
        return builder()
                .matzipId(matzip.getId())
                .avgRating(avgRating)
                .reviewList(reviewList).build();
    }

}
