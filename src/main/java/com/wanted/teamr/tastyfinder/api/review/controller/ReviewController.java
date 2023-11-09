package com.wanted.teamr.tastyfinder.api.review.controller;

import com.wanted.teamr.tastyfinder.api.auth.domain.MemberAdapter;
import com.wanted.teamr.tastyfinder.api.member.domain.Member;
import com.wanted.teamr.tastyfinder.api.review.dto.ReviewRequest;
import com.wanted.teamr.tastyfinder.api.review.dto.ReviewResponse;
import com.wanted.teamr.tastyfinder.api.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/api/reviews/{matzipId}")
    public ResponseEntity<ReviewResponse> createReview(@AuthenticationPrincipal MemberAdapter memberAdapter, @PathVariable("matzipId") Long matzipId, @Valid @RequestBody ReviewRequest request) {
        Member member = memberAdapter.getMember();
        ReviewResponse reviewResponse = reviewService.createReview(member, matzipId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewResponse);
    }

    @PatchMapping("/api/reviews/{reviewId}")
    public ResponseEntity<ReviewResponse> updateReview(@AuthenticationPrincipal MemberAdapter memberAdapter, @PathVariable("reviewId") Long reviewId, @Valid @RequestBody ReviewRequest request) {
        Member member = memberAdapter.getMember();
        ReviewResponse reviewResponse = reviewService.updateReview(member, reviewId, request);
        return ResponseEntity.status(HttpStatus.OK).body(reviewResponse);
    }

    @DeleteMapping("/api/reviews/{reviewId}")
    public ResponseEntity<Void> deleteReview(@AuthenticationPrincipal MemberAdapter memberAdapter, @PathVariable("reviewId") Long reviewId) {
        Member member = memberAdapter.getMember();
        reviewService.deleteReview(member, reviewId);
        return ResponseEntity.ok().build();
    }

}
