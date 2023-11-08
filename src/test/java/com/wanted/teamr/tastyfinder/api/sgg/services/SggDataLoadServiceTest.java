package com.wanted.teamr.tastyfinder.api.sgg.services;

import com.wanted.teamr.tastyfinder.api.sgg.domain.SggData;
import com.wanted.teamr.tastyfinder.api.sgg.repository.SggDataRepository;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.context.annotation.Import;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


@DataRedisTest
@Import(value = {SggDataLoadService.class, SggDataService.class})
class SggDataLoadServiceTest {

    @Autowired
    SggDataLoadService sggDataLoadService;
    @Autowired
    SggDataRepository sggDataRepository;

    @BeforeEach
    void clear() {
        sggDataRepository.deleteAll();
    }

    @DisplayName("지정된 csv 파일로부터 정보를 읽고 Redis의 시군구 데이터를 교체한다..")
    @Test
    void csvExists_thenReplacesSggDataInRedis() throws IOException {
        // given
        String normalResourcePath = "data/sgg_lat_lon.csv";
        boolean skipFirstLine = true;

        // when
        sggDataLoadService.replaceSggDataOnRedis(normalResourcePath, skipFirstLine);

        // then
        List<SggData> sggDataList = new ArrayList<>();
        sggDataRepository.findAll().forEach(sggDataList::add);
        assertThat(sggDataList).hasSize(3)
                .extracting("id", "dosi", "sgg", "lon", "lat")
                .containsExactly(
                        Tuple.tuple("강원:강릉시", "강원", "강릉시", "128.8784972", "37.74913611"),
                        Tuple.tuple("강원:고성군", "강원", "고성군", "128.4701639", "38.37796111"),
                        Tuple.tuple("강원:동해시", "강원", "동해시", "129.1166333", "37.52193056")
                );
    }

    @DisplayName("csv 파일의 첫번째 줄을 skip하지 않을 수 있다.")
    @Test
    void canIncludeFirstLine() throws IOException {
        // given
        String normalResourcePath = "data/sgg_lat_lon.csv";
        boolean skipFirstLine = false;

        // when
        sggDataLoadService.replaceSggDataOnRedis(normalResourcePath, skipFirstLine);

        // then
        List<SggData> sggDataList = new ArrayList<>();
        sggDataRepository.findAll().forEach(sggDataList::add);
        assertThat(sggDataList).hasSize(4);
    }

    @DisplayName("지정된 위치에 csv 파일이 없으면 exception을 발생시킨다.")
    @Test
    void noCsv_thenThrow() {
        // given
        String normalResourcePath = "not_existing_path";
        boolean skipFirstLine = true;

        // when then
        assertThatThrownBy(() -> sggDataLoadService.replaceSggDataOnRedis(normalResourcePath, skipFirstLine))
                .isInstanceOf(IOException.class);
    }


}
