package com.wanted.teamr.tastyfinder.api.sgg.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class SggDataTest {

    @DisplayName("시군구 데이터의 id는 '도시:시군구'가 되어야 한다.")
    @Test
    void SggDataId() {
        // when
        SggData sggData = SggData.from("도시", "시군구", "lon", "lat");

        // then
        assertThat(sggData.getId()).isEqualTo("도시:시군구");
    }

}
