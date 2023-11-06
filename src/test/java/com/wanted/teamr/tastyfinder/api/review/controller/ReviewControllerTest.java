package com.wanted.teamr.tastyfinder.api.review.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.teamr.tastyfinder.api.matzip.domain.Matzip;
import com.wanted.teamr.tastyfinder.api.matzip.repository.MatzipRepository;
import com.wanted.teamr.tastyfinder.api.member.domain.Member;
import com.wanted.teamr.tastyfinder.api.member.repository.MemberRepository;
import com.wanted.teamr.tastyfinder.api.review.dto.ReviewRequest;
import com.wanted.teamr.tastyfinder.api.review.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static com.wanted.teamr.tastyfinder.api.exception.ErrorCode.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("/api/reviews 통합테스트")
@Transactional
public class ReviewControllerTest {

    private final String requestUri = "/api/reviews/{matzipId}";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MatzipRepository matzipRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        Member member = Member.builder()
                .email("test@email.com")
                .password(passwordEncoder.encode("12341234"))
                .build();
        memberRepository.save(member);
    }

    @DisplayName("평가 작성 API")
    @Nested
    class CreateRiview {

        @DisplayName("평가를 작성할 수 있다.")
        @Test
        @WithUserDetails(value = "test@email.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
        void createReview() throws Exception {
            //given
            Matzip matzip = Matzip.builder()
                    .totalRating(0L)
                    .reviewCount(0L).build();
            Long matzipId = matzipRepository.save(matzip).getId();

            ReviewRequest request = ReviewRequest.builder()
                    .rating(5L)
                    .content("맛있어요").build();

            //when, then
            mockMvc.perform(post(requestUri, matzipId)
                            .accept(APPLICATION_JSON)
                            .contentType(APPLICATION_JSON)
                            .content(mapper.writeValueAsString(request)))
                    .andDo(print())
                    .andExpect(status().isCreated());

        }

        @DisplayName("별점 값 없이 평가를 작성할 수 없다.")
        @Test
        @WithUserDetails(value = "test@email.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
        void createReviewFailedByRatingInvalid() throws Exception {
            //given
            Matzip matzip = Matzip.builder()
                    .totalRating(0L)
                    .reviewCount(0L).build();
            Long matzipId = matzipRepository.save(matzip).getId();

            ReviewRequest request = ReviewRequest.builder()
                    .content("맛있어요").build();

            //when, then
            mockMvc.perform(post(requestUri, matzipId)
                            .accept(APPLICATION_JSON)
                            .contentType(APPLICATION_JSON)
                            .content(mapper.writeValueAsString(request)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(REVIEW_RATING_EMPTY.name()))
                    .andExpect(jsonPath("$.message").value(REVIEW_RATING_EMPTY.getMessage()));
        }

        @DisplayName("별점 값이 5보다 클 수 없다.")
        @Test
        @WithUserDetails(value = "test@email.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
        void createReviewFailedByRatingOutOfRange() throws Exception {
            //given
            Matzip matzip = Matzip.builder()
                    .totalRating(0L)
                    .reviewCount(0L).build();
            Long matzipId = matzipRepository.save(matzip).getId();

            ReviewRequest request = ReviewRequest.builder()
                    .rating(6L)
                    .content("맛있어요").build();

            //when, then
            mockMvc.perform(post(requestUri, matzipId)
                            .accept(APPLICATION_JSON)
                            .contentType(APPLICATION_JSON)
                            .content(mapper.writeValueAsString(request)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(REVIEW_RATING_INVALID.name()))
                    .andExpect(jsonPath("$.message").value(REVIEW_RATING_INVALID.getMessage()));
        }

    }

}