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

import java.util.Objects;

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
        matzip.increaseReviewCount();
        return ReviewResponse.of(review);
    }

    @Transactional
    public ReviewResponse updateReview(Member member, Long reviewId, ReviewRequest request) {
        Review review = getReviewIfPresent(reviewId);
        checkMember(member, review);
        review.getMatzip().calculateTotalRating(review, request);
        review.update(request);
        return ReviewResponse.of(review);
    }

    @Transactional
    public void deleteReview(Member member, Long reviewId) {
        Review review = getReviewIfPresent(reviewId);
        checkMember(member, review);
        review.getMatzip().decreaseTotalRating(review);
        review.getMatzip().decreaseReviewCount();
        reviewRepository.deleteById(review.getId());
    }

    public Matzip getMatzipIfPresent(Long matzipId) {
        return matzipRepository.findById(matzipId)
                .orElseThrow(() -> new CustomException(ErrorCode.MATZIP_NOT_FOUND));
    }

    public Review getReviewIfPresent(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));
    }

    public void checkMember(Member member, Review review) {
        if (!Objects.equals(review.getMember().getId(), member.getId())) {
            throw new CustomException(ErrorCode.MEMBER_NOT_SAME);
        }

    }

}
