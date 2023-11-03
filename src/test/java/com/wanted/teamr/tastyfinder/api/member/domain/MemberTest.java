package com.wanted.teamr.tastyfinder.api.member.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.wanted.teamr.tastyfinder.api.member.dto.MemberUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberTest {

    private Member member;

    @BeforeEach
    void setUp() {
        member = Member.builder()
                .email("test@test.com")
                .latitude("35.1111")
                .longitude("125.1111")
                .isRecommendEnabled(true)
                .build();
    }

    @Test
    @DisplayName("사용자 정보를 수정할 수 있다.")
    void update() {
        String latitude = "35.1111";
        String longitude = "125.1111";
        Boolean isRecommendEnabled = false;
        MemberUpdateRequest memberUpdateRequest = MemberUpdateRequest.builder()
                .latitude(latitude)
                .longitude(longitude)
                .isRecommendEnabled(isRecommendEnabled)
                .build();

        //when
        member.update(memberUpdateRequest);

        //then
        assertThat(member.getLatitude()).isEqualTo(latitude);
        assertThat(member.getLongitude()).isEqualTo(longitude);
        assertThat(member.getIsRecommendEnabled()).isEqualTo(isRecommendEnabled);
    }
}
