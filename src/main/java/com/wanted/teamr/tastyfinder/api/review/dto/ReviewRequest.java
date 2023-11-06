package com.wanted.teamr.tastyfinder.api.review.dto;

import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewRequest {

    @NotNull(message = "REVIEW_RATING_EMPTY")
    @Min(value = 0, message = "REVIEW_RATING_INVALID")
    @Max(value = 5, message = "REVIEW_RATING_INVALID")
    private Long rating;

    private String content;

    @Builder
    public ReviewRequest(Long rating, String content) {
        this.rating = rating;
        this.content = content;
    }

}