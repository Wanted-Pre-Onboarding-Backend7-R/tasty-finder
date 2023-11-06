package com.wanted.teamr.tastyfinder.api.matzip.service;

import com.wanted.teamr.tastyfinder.api.exception.CustomException;
import com.wanted.teamr.tastyfinder.api.exception.ErrorCode;
import com.wanted.teamr.tastyfinder.api.matzip.domain.Matzip;
import com.wanted.teamr.tastyfinder.api.matzip.repository.MatzipRepository;
import com.wanted.teamr.tastyfinder.api.review.domain.Review;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class MatzipServiceMockTest {

    @Mock
    MatzipRepository matzipRepository;
    @InjectMocks
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
            Matzip matzip = Matzip.builder()
                    .totalRating(6L)
                    .reviewCount(2L).build();
            List<Review> reviewResponseList = new ArrayList<>();
            Review review1 = Review.builder()
                    .matzip(matzip)
                    .rating(5L)
                    .content("맛있어요").build();
            Review review2 = Review.builder()
                    .matzip(matzip)
                    .rating(1L)
                    .content("맛없어요").build();
            reviewResponseList.add(review1);
            reviewResponseList.add(review2);
            given(matzipRepository.findById(matzipId)).willReturn(Optional.of(matzip));

            // when
            matzipService.getMatzip(matzipId);

            // then
            assertAll(
                    () -> verify(matzipRepository).findById(matzipId)
            );
        }

        @DisplayName("맛집을 찾을 수 없을 때 에러 응답을 보낸다.")
        @WithMockUser
        @Test
        void getPostNotFound() throws Exception {
            // given
            Long matzipId = 100L;
            given(matzipRepository.findById(matzipId)).willReturn(Optional.empty());

            // when, then
            assertThatThrownBy(() -> matzipService.getMatzip(matzipId))
                    .isInstanceOf(CustomException.class)
                    .extracting("errorCode")
                    .isEqualTo(ErrorCode.MATZIP_NOT_FOUND);
        }

    }

}
