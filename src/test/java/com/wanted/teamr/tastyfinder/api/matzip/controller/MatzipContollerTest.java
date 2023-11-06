package com.wanted.teamr.tastyfinder.api.matzip.controller;

import com.wanted.teamr.tastyfinder.api.exception.ErrorCode;
import com.wanted.teamr.tastyfinder.api.matzip.domain.Matzip;
import com.wanted.teamr.tastyfinder.api.matzip.repository.MatzipRepository;
import com.wanted.teamr.tastyfinder.api.review.domain.Review;
import com.wanted.teamr.tastyfinder.api.review.repository.ReviewRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("/api/matzips 통합 테스트")
public class MatzipContollerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    MatzipRepository matzipRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @BeforeEach
    void setup() {
        matzipRepository.deleteAll();
    }

    @DisplayName("맛집 상세 정보 API")
    @Nested
    class GetMatzip {

        @DisplayName("맛집 조회 시 맛집 상세 정보 응답을 보낸다 - 평가 수정 시간 내림차순으로 정렬된다. ")
        @WithMockUser
        @Test
        void getMatzipFound() throws Exception {
            // given
            Matzip matzip = Matzip.builder()
                    .totalRating(6L)
                    .reviewCount(2L).build();
            matzipRepository.save(matzip);

            Review review1 = Review.builder()
                    .matzip(matzip)
                    .rating(4L)
                    .content("맛있어요").build();
            Review review2 = Review.builder()
                    .matzip(matzip)
                    .rating(2L)
                    .content("맛없어요").build();
            reviewRepository.save(review1);
            reviewRepository.save(review2);

            Long matzipId = matzip.getId();

            // when, then
            mockMvc.perform(get("/api/matzips/{matzipId}", matzipId))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.matzipId").value(1))
                    .andExpect(jsonPath("$.avgRating").value(3))
                    .andExpect(jsonPath("$.reviewList", hasSize(2)))
                    .andExpect(jsonPath("$.reviewList").isArray())
                    .andExpect(jsonPath("$.reviewList[0].reviewId").value(review2.getId()))
                    .andExpect(jsonPath("$.reviewList[1].reviewId").value(review1.getId()));
        }

        @DisplayName("맛집을 찾을 수 없을 때 에러 응답을 보낸다.")
        @WithMockUser
        @Test
        void getPostNotFound() throws Exception {
            // given
            List<Matzip> all = matzipRepository.findAll();
            Assertions.assertThat(all).hasSize(0);

            // when, then
            mockMvc.perform(get("/api/matzips/{matzipId}", 1L))
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.code").value(ErrorCode.MATZIP_NOT_FOUND.name()))
                    .andExpect(jsonPath("$.message").value(ErrorCode.MATZIP_NOT_FOUND.getMessage()));
        }

    }

}
