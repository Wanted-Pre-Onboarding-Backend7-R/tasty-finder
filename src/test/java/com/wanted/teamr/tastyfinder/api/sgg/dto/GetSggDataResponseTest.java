package com.wanted.teamr.tastyfinder.api.sgg.dto;

import com.wanted.teamr.tastyfinder.api.sgg.domain.SggData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


class GetSggDataResponseTest {

    @DisplayName("from 스태틱 메소드를 이용해 SggData로부터 GetSggDataResponse 객체를 만들 수 있다.")
    @Test
    void from() {
        // given
        SggData sggData = SggData.from("도시1", "시군구1", "lon1", "lat1");

        // when
        GetSggDataResponse getSggDataResponse = GetSggDataResponse.from(sggData);

        // then
        assertThat(getSggDataResponse.getId()).isEqualTo(sggData.getId());
        assertThat(getSggDataResponse.getDosi()).isEqualTo(sggData.getDosi());
        assertThat(getSggDataResponse.getSgg()).isEqualTo(sggData.getSgg());
        assertThat(getSggDataResponse.getLon()).isEqualTo(sggData.getLon());
        assertThat(getSggDataResponse.getLat()).isEqualTo(sggData.getLat());
    }

}
