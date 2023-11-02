package com.wanted.teamr.tastyfinder.api.matzip.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LocationTest {

    @Test
    @DisplayName("두 위치에서 서로 각각 거리를 구할 때 같은 거리 결과가 나와야 한다")
    void calcDistance() {
        Location loc1 = Location.of("37.44749167", "127.1477194");
        Location loc2 = Location.of("37.205", "127.1385");;

        assertThat(loc1.distanceFrom(loc2)).isEqualTo(loc2.distanceFrom(loc1));
    }
}
