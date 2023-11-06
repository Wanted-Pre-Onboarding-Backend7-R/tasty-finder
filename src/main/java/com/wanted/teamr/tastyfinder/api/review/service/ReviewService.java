package com.wanted.teamr.tastyfinder.api.review.service;

import com.wanted.teamr.tastyfinder.api.exception.CustomException;
import com.wanted.teamr.tastyfinder.api.exception.ErrorCode;
import com.wanted.teamr.tastyfinder.api.review.domain.Review;
import com.wanted.teamr.tastyfinder.api.review.dto.ReviewRequest;
import com.wanted.teamr.tastyfinder.api.review.dto.ReviewResponse;
import com.wanted.teamr.tastyfinder.api.review.repository.ReviewRepository;
import com.wanted.teamr.tastyfinder.api.matzip.domain.Matzip;
import com.wanted.teamr.tastyfinder.api.matzip.repository.MatzipRepository;
import com.wanted.teamr.tastyfinder.api.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MatzipRepository matzipRepository;

    @Transactional
    public ReviewResponse createReview(Member member, Long matzipId, ReviewRequest request) {
        Matzip matzip = getMatzipIfPresent(matzipId);
        Review review = Review.of(request, member, matzip);
        review = reviewRepository.save(review);
        matzip.updateTotalRating(review);
        matzip.updateReviewCount();
        return ReviewResponse.of(review);
    }

    public Matzip getMatzipIfPresent(Long matzipId) {
        return matzipRepository.findById(matzipId)
                .orElseThrow(() -> new CustomException(ErrorCode.MATZIP_NOT_FOUND));
    }

}
