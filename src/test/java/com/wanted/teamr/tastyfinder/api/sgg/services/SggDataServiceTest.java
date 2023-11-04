package com.wanted.teamr.tastyfinder.api.sgg.services;

import com.wanted.teamr.tastyfinder.api.sgg.domain.SggData;
import com.wanted.teamr.tastyfinder.api.sgg.repository.SggDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@DataRedisTest
@Import(SggDataService.class)
class SggDataServiceTest {

    @Autowired
    SggDataRepository sggDataRepository;
    @Autowired
    SggDataService sggDataService;

    @BeforeEach
    void clear() {
        sggDataRepository.deleteAll();
    }

    @DisplayName("sggDataService::replaceAll은 저장된 SggData를 입력받은 SggData 교체해야 한다.")
    @Test
    void sggDataServiceReplaceAll() {
        // given - 기존 저장된 SggData 한 건
        SggData sggData = SggData.from("도시1", "시군구1", "lon1", "lat1");
        sggDataRepository.save(sggData);
        assertThat(sggDataRepository.findAll()).hasSize(1);

        List<SggData> sggDataList = List.of(
                SggData.from("도시2", "시군구2", "lon2", "lat2"),
                SggData.from("도시3", "시군구3", "lon3", "lat3"),
                SggData.from("도시4", "시군구4", "lon4", "lat4")
        );

        // when
        sggDataService.replaceAll(sggDataList);

        // then
        Iterable<SggData> all = sggDataRepository.findAll();
        assertThat(all).hasSize(3)
                .extracting("dosi")
                .containsExactly("도시2", "도시3", "도시4");
    }
}
