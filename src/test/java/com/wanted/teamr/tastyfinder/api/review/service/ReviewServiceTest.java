package com.wanted.teamr.tastyfinder.api.review.service;

import com.wanted.teamr.tastyfinder.api.exception.CustomException;
import com.wanted.teamr.tastyfinder.api.exception.ErrorCode;
import com.wanted.teamr.tastyfinder.api.matzip.domain.Matzip;
import com.wanted.teamr.tastyfinder.api.matzip.repository.MatzipRepository;
import com.wanted.teamr.tastyfinder.api.member.domain.Member;
import com.wanted.teamr.tastyfinder.api.member.repository.MemberRepository;
import com.wanted.teamr.tastyfinder.api.review.domain.Review;
import com.wanted.teamr.tastyfinder.api.review.dto.ReviewRequest;
import com.wanted.teamr.tastyfinder.api.review.dto.ReviewResponse;
import com.wanted.teamr.tastyfinder.api.review.repository.ReviewRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@DisplayName("평가 서비스 통합 테스트")
@Transactional
public class ReviewServiceTest {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    MatzipRepository matzipRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ReviewService reviewService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Member member;

    @BeforeEach
    void setUp() {
        member = Member.builder()
                .email("test@email.com")
                .password(passwordEncoder.encode("12341234"))
                .build();
        memberRepository.save(member);
        matzipRepository.deleteAll();
    }

    @DisplayName("평가 작성 API")
    @Nested
    class CreateReview {

        @DisplayName("평가 작성 성공 - 맛집의 총 별점이 평가 작성시 별점 만큼 늘어나고, 평가 개수가 1 증가한다.")
        @WithUserDetails(value = "test@email.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @Test
        void createReview() throws Exception {
            // given
            Long totalRating = 0L;
            Long reviewCount = 0L;
            Long rating = 5L;
            Matzip matzip = Matzip.builder()
                    .totalRating(totalRating)
                    .reviewCount(reviewCount).build();
            Long matzipId = matzipRepository.save(matzip).getId();

            ReviewRequest request = ReviewRequest.builder()
                    .rating(rating)
                    .content("맛있어요").build();

            // when
            ReviewResponse reviewResponse = reviewService.createReview(member, matzipId, request);

            // then
            Review review = reviewRepository.findById(reviewResponse.getReviewId()).orElseThrow();
            Matzip matzip1 = review.getMatzip();
            assertThat(review.getRating()).isEqualTo(request.getRating());
            assertThat(review.getContent()).isEqualTo(request.getContent());
            assertThat(matzip1.getTotalRating()).isEqualTo(totalRating + rating);
            assertThat(matzip1.getReviewCount()).isEqualTo(reviewCount + 1L);
        }

        @DisplayName("평가 작성 실패 - 맛집 게시물을 찾을 수 없음")
        @WithUserDetails(value = "test@email.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @Test
        void createReviewFailedBy() throws Exception {
            // given
            Long rating = 5L;
            Long matzipId = 0L;
            ReviewRequest request = ReviewRequest.builder()
                    .rating(rating)
                    .content("맛있어요").build();

            // when, then
            assertThatThrownBy(() -> reviewService.createReview(member, matzipId, request))
                    .isInstanceOf(CustomException.class)
                    .hasMessage(ErrorCode.MATZIP_NOT_FOUND.getMessage());
        }

    }

}