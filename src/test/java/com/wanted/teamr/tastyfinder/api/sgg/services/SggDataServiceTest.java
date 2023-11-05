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

    @DisplayName("SggDataService::replaceAll은 저장된 시군구 데이터를 입력받은 시군구 데이터로 교체해야 한다.")
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

    @DisplayName("SggDataService::getSggDataList는 저장된 시군구 데이터 리스트를 반환한다.")
    @Test
    void sggDataServiceGetDataList() {
        // given
        SggData sggData1 = SggData.from("도시1", "시군구1", "lon1", "lat1");
        SggData sggData2 = SggData.from("도시2", "시군구2", "lon2", "lat2");
        sggDataRepository.saveAll(List.of(sggData1, sggData2));
        assertThat(sggDataRepository.findAll()).hasSize(2);

        // when
        List<SggData> sggDataList = sggDataService.getSggDataList();

        // then
        assertThat(sggDataList).hasSize(2)
                .extracting("dosi")
                .containsExactly("도시1", "도시2");
    }

    @DisplayName("SggDataService::getSggDataList는 저장된 시군구 데이터가 없을 경우 빈 리스트를 반환한다.")
    @Test
    void sggDataServiceGetDataListEmpty() {
        // given
        assertThat(sggDataRepository.findAll()).hasSize(0);

        // when
        List<SggData> sggDataList = sggDataService.getSggDataList();

        // then
        assertThat(sggDataList).hasSize(0);
    }

}
