package com.wanted.teamr.tastyfinder.api.matzip.service;

import com.wanted.teamr.tastyfinder.api.exception.CustomException;
import com.wanted.teamr.tastyfinder.api.exception.ErrorCode;
import com.wanted.teamr.tastyfinder.api.matzip.domain.Matzip;
import com.wanted.teamr.tastyfinder.api.matzip.dto.MatzipResponse;
import com.wanted.teamr.tastyfinder.api.matzip.repository.MatzipRepository;
import com.wanted.teamr.tastyfinder.api.review.domain.Review;
import com.wanted.teamr.tastyfinder.api.review.dto.ReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatzipService {

    private final MatzipRepository matzipRepository;

    @Transactional(readOnly = true)
    public MatzipResponse getMatzip(Long matzipId) {
        Matzip matzip = getMatzipIfPresent(matzipId);
        int avgRating = calculateAvgRating(matzip);
        List<ReviewResponse> responseList = getReviewResponseList(matzip);
        return MatzipResponse.of(matzip, avgRating, responseList);
    }

    public Matzip getMatzipIfPresent(Long matzipId) {
        return matzipRepository.findById(matzipId)
                .orElseThrow(() -> new CustomException(ErrorCode.MATZIP_NOT_FOUND));
    }

    public int calculateAvgRating(Matzip matzip) {
        return matzip.getTotalRating() / matzip.getReviewCount();
    }

    public List<ReviewResponse> getReviewResponseList(Matzip matzip) {
        List<Review> reviewList = matzipRepository.findReviewByMatzipIdOrderByModifiedAtDesc(matzip.getId());
        List<ReviewResponse> responseList = new ArrayList<>();
        for(Review review : reviewList) {
            ReviewResponse reviewResponse = ReviewResponse.of(review);
            responseList.add(reviewResponse);
        }
        return responseList;
    }

}
