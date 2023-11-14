package com.wanted.teamr.tastyfinder.api.matzip.controller;

import com.wanted.teamr.tastyfinder.api.exception.domain.CustomException;
import com.wanted.teamr.tastyfinder.api.exception.domain.ErrorCode;
import com.wanted.teamr.tastyfinder.api.matzip.dto.MatzipResponse;
import com.wanted.teamr.tastyfinder.api.matzip.service.MatzipService;
import com.wanted.teamr.tastyfinder.api.review.dto.ReviewResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = MatzipController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class MatzipControllerMockTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MatzipService matzipService;

    @DisplayName("맛집 상세 정보 API")
    @Nested
    class GetMatzip {

        @DisplayName("맛집 조회 시 맛집 상세 정보 응답을 보낸다.")
        @WithMockUser
        @Test
        void getMatzipFound() throws Exception {
            // given
            Long matzipId = 1L;
            List<ReviewResponse> reviewResponseList = new ArrayList<>();
            ReviewResponse reviewResponse1 = ReviewResponse.builder()
                    .reviewId(1L)
                    .rating(5L)
                    .content("맛있어요").build();
            ReviewResponse reviewResponse2 = ReviewResponse.builder()
                    .reviewId(2L)
                    .rating(1L)
                    .content("맛없어요").build();
            reviewResponseList.add(reviewResponse1);
            reviewResponseList.add(reviewResponse2);
            MatzipResponse matzipResponse = MatzipResponse.builder()
                    .matzipId(matzipId)
                    .reviewList(reviewResponseList)
                    .avgRating(3).build();
            when(matzipService.getMatzip(matzipId)).thenReturn(matzipResponse);

            // when, then
            mockMvc.perform(get("/api/matzips/{matzipId}", matzipId))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.matzipId").value(1))
                    .andExpect(jsonPath("$.avgRating").value(3))
                    .andExpect(jsonPath("$.reviewList", hasSize(2)))
                    .andExpect(jsonPath("$.reviewList").isArray())
                    .andExpect(jsonPath("$.reviewList[0].reviewId").value(reviewResponse1.getReviewId()))
                    .andExpect(jsonPath("$.reviewList[1].reviewId").value(reviewResponse2.getReviewId()));
        }

        @DisplayName("맛집을 찾을 수 없을 때 에러 응답을 보낸다.")
        @WithMockUser
        @Test
        void getPostNotFound() throws Exception {
            // given
            Long matzipId = 100L;
            when(matzipService.getMatzip(matzipId)).thenThrow(new CustomException(ErrorCode.MATZIP_NOT_FOUND));

            // when, then
            mockMvc.perform(get("/api/matzips/{matzipId}", matzipId))
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.code").value(ErrorCode.MATZIP_NOT_FOUND.name()))
                    .andExpect(jsonPath("$.message").value(ErrorCode.MATZIP_NOT_FOUND.getMessage()));
        }

    }

}
