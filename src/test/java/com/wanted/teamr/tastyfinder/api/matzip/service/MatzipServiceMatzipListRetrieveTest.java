package com.wanted.teamr.tastyfinder.api.matzip.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanted.teamr.tastyfinder.api.matzip.domain.Location;
import com.wanted.teamr.tastyfinder.api.matzip.domain.MatzipListRetrieveCategory;
import com.wanted.teamr.tastyfinder.api.matzip.domain.MatzipListRetrieveType;
import com.wanted.teamr.tastyfinder.api.matzip.dto.MatzipListRetrieveRequest;
import com.wanted.teamr.tastyfinder.api.matzip.dto.MatzipSummaryReponse;
import com.wanted.teamr.tastyfinder.api.matzip.fixture.LocationFixture;
import com.wanted.teamr.tastyfinder.api.matzip.repository.MatzipRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * <p>
 * 맛집 목록 조회 Service layer 테스트
 * </p>
 * <p>
 * 따로 불러와야되는 더미 데이터(matzip.sql)가 있어서
 * 본래 팀에서 정한 클래스 이름 규칙(MatzipServiceTest)과 다르게 생성함
 * </p>
 * 맛집과의 거리 distance와 몇 km 이내의 맛집 조회 조건 range는 "1.0" 와 같은 km 단위이나
 * 함수명으로 .을 표기할 수 없고 1_0 와 같은 형태도 애매하다고 생각해 meter로 표기함
 */
@Transactional
@SpringBootTest
@DisplayName("맛집 목록 조회 Service 통합 테스트")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MatzipServiceMatzipListRetrieveTest {

    @Autowired
    private MatzipService matzipService;

    // 시군구 데이터 성남시
    private final Location requestLocation = LocationFixture.LOCATION_SEONG_NAM;

    /**
     * <p>
     * local에서 matzip entity만 설정해서 테스트할 때는 문제 없었는데
     * matzip_row도 만들고 테스트하니 여러 개의 테스트 중 첫 번째만 통과하고 나머지 다 실패
     * </p>
     *
     * {@code @Sql("/db/matzip.sql")}는 매 테스트마다 실행된다고 함. 그래서 실패한 것 같고
     * 다른 방법도 있겠지만 일단 beforeAll에서 수동으로 한 번만 로딩되도록 설정
     */
    @BeforeAll
    static void beforeAll(@Autowired DataSource dataSource) {
        try (Connection conn = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("db/matzip.sql"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("요청 위치 주변 0.01km 이내의 맛집 목록을 거리순으로 조회 했는데 없으면 비어있는 list를 반환한다")
    void retrieveMatzipList_distance_10m() {
        // given
        String range = "0.01";
        MatzipListRetrieveType type = MatzipListRetrieveType.DISTANCE;
        MatzipListRetrieveRequest request = MatzipListRetrieveRequest.builder()
                                                                     .lat(requestLocation.getLat())
                                                                     .lon(requestLocation.getLon())
                                                                     .range(range)
                                                                     .type(type)
                                                                     .build();

        // when
        List<MatzipSummaryReponse> result = matzipService.retrieveMatzipList(request);

        // then
        assertThat(result.size()).isEqualTo(0);
    }


    @Test
    @DisplayName("요청 위치 주변 1.0km 이내의 맛집 목록을 거리순으로 조회한다")
    void retrieveMatzipList_distance_1000m() {
        // given
        String range = "1.0";
        MatzipListRetrieveType type = MatzipListRetrieveType.DISTANCE;
        MatzipListRetrieveRequest request = MatzipListRetrieveRequest.builder()
                                                                     .lat(requestLocation.getLat())
                                                                     .lon(requestLocation.getLon())
                                                                     .range(range)
                                                                     .type(type)
                                                                     .build();
        List<String> expected = Arrays.asList("맛집 2", "맛집 1");

        // when
        List<MatzipSummaryReponse> result = matzipService.retrieveMatzipList(request);

        // then
        assertAll(
                () -> assertThat(result.size()).isEqualTo(2),
                () -> assertThat(result).extracting(MatzipSummaryReponse::getName)
                                        .containsExactlyElementsOf(expected)
        );
    }

    @Test
    @DisplayName("요청 위치 주변 5.0km 이내의 맛집 목록을 거리순으로 조회한다")
    void retrieveMatzipList_distance_5000m() {
        // given
        String range = "5.0";
        MatzipListRetrieveType type = MatzipListRetrieveType.DISTANCE;
        MatzipListRetrieveRequest request = MatzipListRetrieveRequest.builder()
                                                                     .lat(requestLocation.getLat())
                                                                     .lon(requestLocation.getLon())
                                                                     .range(range)
                                                                     .type(type)
                                                                     .build();
        List<String> expected = Arrays.asList("맛집 2", "맛집 1", "맛집 3", "맛집 5", "맛집 7", "맛집 6");

        // when
        List<MatzipSummaryReponse> result = matzipService.retrieveMatzipList(request);

        // then
        assertAll(
                () -> assertThat(result.size()).isEqualTo(expected.size()),
                () -> assertThat(result).extracting(MatzipSummaryReponse::getName)
                                        .containsExactlyElementsOf(expected)
        );
    }

    @Test
    @DisplayName("요청 위치 주변 10.0km 이내의 맛집 목록을 거리순으로 조회한다")
    void retrieveMatzipList_distance_10000m() {
        // given
        String range = "10.0";
        MatzipListRetrieveType type = MatzipListRetrieveType.DISTANCE;
        MatzipListRetrieveRequest request = MatzipListRetrieveRequest.builder()
                                                                     .lat(requestLocation.getLat())
                                                                     .lon(requestLocation.getLon())
                                                                     .range(range)
                                                                     .type(type)
                                                                     .build();
        List<String> expected = Arrays.asList(
                "맛집 2", "맛집 1", "맛집 3", "맛집 5", "맛집 7",
                "맛집 6", "맛집 4", "맛집 8", "맛집 11", "맛집 9"
        );

        // when
        List<MatzipSummaryReponse> result = matzipService.retrieveMatzipList(request);

        // then
        assertAll(
                () -> assertThat(result.size()).isEqualTo(expected.size()),
                () -> assertThat(result).extracting(MatzipSummaryReponse::getName)
                                        .containsExactlyElementsOf(expected)
        );
    }

    @Test
    @DisplayName("요청 위치 주변 0.01km 이내의 맛집 목록을 평점 순으로 조회 했는데 없으면 비어있는 list를 반환한다")
    void retrieveMatzipList_avgRating_10m() {
        // given
        String range = "0.01";
        MatzipListRetrieveType type = MatzipListRetrieveType.AVG_RATING;
        MatzipListRetrieveRequest request = MatzipListRetrieveRequest.builder()
                                                                     .lat(requestLocation.getLat())
                                                                     .lon(requestLocation.getLon())
                                                                     .range(range)
                                                                     .type(type)
                                                                     .build();

        // when
        List<MatzipSummaryReponse> result = matzipService.retrieveMatzipList(request);

        // then
        assertThat(result.size()).isEqualTo(0);
    }


    @Test
    @DisplayName("요청 위치 주변 1.0km 이내의 맛집 목록을 평점 순으로 조회한다")
    void retrieveMatzipList_avgRating_1000m() {
        // given
        String range = "1.0";
        MatzipListRetrieveType type = MatzipListRetrieveType.AVG_RATING;
        MatzipListRetrieveRequest request = MatzipListRetrieveRequest.builder()
                                                                     .lat(requestLocation.getLat())
                                                                     .lon(requestLocation.getLon())
                                                                     .range(range)
                                                                     .type(type)
                                                                     .build();
        List<String> expected = Arrays.asList("맛집 1", "맛집 2");

        // when
        List<MatzipSummaryReponse> result = matzipService.retrieveMatzipList(request);

        // then
        assertAll(
                () -> assertThat(result.size()).isEqualTo(2),
                () -> assertThat(result).extracting(MatzipSummaryReponse::getName)
                                        .containsExactlyElementsOf(expected)
        );
    }

    @Test
    @DisplayName("요청 위치 주변 5.0km 이내의 맛집 목록을 평점 순으로 조회한다")
    void retrieveMatzipList_avgRating_5000m() {
        // given
        String range = "5.0";
        MatzipListRetrieveType type = MatzipListRetrieveType.AVG_RATING;
        MatzipListRetrieveRequest request = MatzipListRetrieveRequest.builder()
                                                                     .lat(requestLocation.getLat())
                                                                     .lon(requestLocation.getLon())
                                                                     .range(range)
                                                                     .type(type)
                                                                     .build();
        List<String> expected = Arrays.asList("맛집 3", "맛집 7", "맛집 1", "맛집 2", "맛집 5", "맛집 6");

        // when
        List<MatzipSummaryReponse> result = matzipService.retrieveMatzipList(request);

        // then
        assertAll(
                () -> assertThat(result.size()).isEqualTo(expected.size()),
                () -> assertThat(result).extracting(MatzipSummaryReponse::getName)
                                        .containsExactlyElementsOf(expected)
        );
    }

    @Test
    @DisplayName("요청 위치 주변 10.0km 이내의 맛집 목록을 평점 순으로 조회한다")
    void retrieveMatzipList_avgRating_10000m() {
        // given
        String range = "10.0";
        MatzipListRetrieveType type = MatzipListRetrieveType.AVG_RATING;
        MatzipListRetrieveRequest request = MatzipListRetrieveRequest.builder()
                                                                     .lat(requestLocation.getLat())
                                                                     .lon(requestLocation.getLon())
                                                                     .range(range)
                                                                     .type(type)
                                                                     .build();
        List<String> expected = Arrays.asList(
                "맛집 9", "맛집 3", "맛집 11", "맛집 7", "맛집 1",
                "맛집 2", "맛집 4", "맛집 5", "맛집 6", "맛집 8"
        );

        // when
        List<MatzipSummaryReponse> result = matzipService.retrieveMatzipList(request);

        // then
        assertAll(
                () -> assertThat(result.size()).isEqualTo(expected.size()),
                () -> assertThat(result).extracting(MatzipSummaryReponse::getName)
                                        .containsExactlyElementsOf(expected)
        );
    }

    @Test
    @DisplayName("요청 위치 주변 5.0km 이내의 맛집 목록을 거리순으로 카페만 조회한다")
    void retrieveMatzipList_distance_5000m_cafe() {
        // given
        String range = "5.0";
        MatzipListRetrieveType type = MatzipListRetrieveType.DISTANCE;
        MatzipListRetrieveCategory category = MatzipListRetrieveCategory.CAFE;
        MatzipListRetrieveRequest request = MatzipListRetrieveRequest.builder()
                                                                     .lat(requestLocation.getLat())
                                                                     .lon(requestLocation.getLon())
                                                                     .range(range)
                                                                     .type(type)
                                                                     .category(category)
                                                                     .build();
        List<String> expected = Arrays.asList("맛집 2", "맛집 5");

        // when
        List<MatzipSummaryReponse> result = matzipService.retrieveMatzipList(request);

        // then
        assertAll(
                () -> assertThat(result.size()).isEqualTo(expected.size()),
                () -> assertThat(result).extracting(MatzipSummaryReponse::getName)
                                        .containsExactlyElementsOf(expected)
        );
    }

    @Test
    @DisplayName("요청 위치 주변 5.0km 이내의 맛집 목록을 거리순으로 일식만 조회한다")
    void retrieveMatzipList_distance_5000m_jpn() {
        // given
        String range = "5.0";
        MatzipListRetrieveType type = MatzipListRetrieveType.DISTANCE;
        MatzipListRetrieveCategory category = MatzipListRetrieveCategory.JPN;
        MatzipListRetrieveRequest request = MatzipListRetrieveRequest.builder()
                                                                     .lat(requestLocation.getLat())
                                                                     .lon(requestLocation.getLon())
                                                                     .range(range)
                                                                     .type(type)
                                                                     .category(category)
                                                                     .build();
        List<String> expected = Arrays.asList("맛집 1", "맛집 3");

        // when
        List<MatzipSummaryReponse> result = matzipService.retrieveMatzipList(request);

        // then
        assertAll(
                () -> assertThat(result.size()).isEqualTo(expected.size()),
                () -> assertThat(result).extracting(MatzipSummaryReponse::getName)
                                        .containsExactlyElementsOf(expected)
        );
    }

    @Test
    @DisplayName("요청 위치 주변 5.0km 이내의 맛집 목록을 거리순으로 중식만 조회한다")
    void retrieveMatzipList_distance_5000m_chn() {
        // given
        String range = "5.0";
        MatzipListRetrieveType type = MatzipListRetrieveType.DISTANCE;
        MatzipListRetrieveCategory category = MatzipListRetrieveCategory.CHN;
        MatzipListRetrieveRequest request = MatzipListRetrieveRequest.builder()
                                                                     .lat(requestLocation.getLat())
                                                                     .lon(requestLocation.getLon())
                                                                     .range(range)
                                                                     .type(type)
                                                                     .category(category)
                                                                     .build();
        List<String> expected = Arrays.asList("맛집 7", "맛집 6");

        // when
        List<MatzipSummaryReponse> result = matzipService.retrieveMatzipList(request);

        // then
        assertAll(
                () -> assertThat(result.size()).isEqualTo(expected.size()),
                () -> assertThat(result).extracting(MatzipSummaryReponse::getName)
                                        .containsExactlyElementsOf(expected)
        );
    }

    @Test
    @DisplayName("요청 위치 주변 5.0km 이내의 맛집 목록을 평점 순으로 카페만 조회한다")
    void retrieveMatzipList_avgRating_5000m_cafe() {
        // given
        String range = "5.0";
        MatzipListRetrieveType type = MatzipListRetrieveType.AVG_RATING;
        MatzipListRetrieveCategory category = MatzipListRetrieveCategory.CAFE;
        MatzipListRetrieveRequest request = MatzipListRetrieveRequest.builder()
                                                                     .lat(requestLocation.getLat())
                                                                     .lon(requestLocation.getLon())
                                                                     .range(range)
                                                                     .type(type)
                                                                     .category(category)
                                                                     .build();
        List<String> expected = Arrays.asList("맛집 2", "맛집 5");

        // when
        List<MatzipSummaryReponse> result = matzipService.retrieveMatzipList(request);

        // then
        assertAll(
                () -> assertThat(result.size()).isEqualTo(expected.size()),
                () -> assertThat(result).extracting(MatzipSummaryReponse::getName)
                                        .containsExactlyElementsOf(expected)
        );
    }

    @Test
    @DisplayName("요청 위치 주변 5.0km 이내의 맛집 목록을 평점 순으로 일식만 조회한다")
    void retrieveMatzipList_avgRating_5000m_jpn() {
        // given
        String range = "5.0";
        MatzipListRetrieveType type = MatzipListRetrieveType.AVG_RATING;
        MatzipListRetrieveCategory category = MatzipListRetrieveCategory.JPN;
        MatzipListRetrieveRequest request = MatzipListRetrieveRequest.builder()
                                                                     .lat(requestLocation.getLat())
                                                                     .lon(requestLocation.getLon())
                                                                     .range(range)
                                                                     .type(type)
                                                                     .category(category)
                                                                     .build();
        List<String> expected = Arrays.asList("맛집 3", "맛집 1");

        // when
        List<MatzipSummaryReponse> result = matzipService.retrieveMatzipList(request);

        // then
        assertAll(
                () -> assertThat(result.size()).isEqualTo(expected.size()),
                () -> assertThat(result).extracting(MatzipSummaryReponse::getName)
                                        .containsExactlyElementsOf(expected)
        );
    }

    @Test
    @DisplayName("요청 위치 주변 5.0km 이내의 맛집 목록을 평점 순으로 중식만 조회한다")
    void retrieveMatzipList_avgRating_5000m_chn() {
        // given
        String range = "5.0";
        MatzipListRetrieveType type = MatzipListRetrieveType.AVG_RATING;
        MatzipListRetrieveCategory category = MatzipListRetrieveCategory.CHN;
        MatzipListRetrieveRequest request = MatzipListRetrieveRequest.builder()
                                                                     .lat(requestLocation.getLat())
                                                                     .lon(requestLocation.getLon())
                                                                     .range(range)
                                                                     .type(type)
                                                                     .category(category)
                                                                     .build();
        List<String> expected = Arrays.asList("맛집 7", "맛집 6");

        // when
        List<MatzipSummaryReponse> result = matzipService.retrieveMatzipList(request);

        // then
        assertAll(
                () -> assertThat(result.size()).isEqualTo(expected.size()),
                () -> assertThat(result).extracting(MatzipSummaryReponse::getName)
                                        .containsExactlyElementsOf(expected)
        );
    }

    @Test
    @DisplayName("요청 위치 주변 2.0km 이내의 맛집 목록을 조회하면 기본으로 모든 음식점 종류에 대해 거리순으로 조회한다")
    void retrieveMatzipList_range_2000m_default() throws Exception {
        // given
        String range = "2.0";
        MatzipListRetrieveRequest request = MatzipListRetrieveRequest.builder()
                                                                     .lat(requestLocation.getLat())
                                                                     .lon(requestLocation.getLon())
                                                                     .range(range)
                                                                     .build();
        List<String> expected = Arrays.asList("맛집 2", "맛집 1", "맛집 3", "맛집 5");

        // when
        List<MatzipSummaryReponse> result = matzipService.retrieveMatzipList(request);

        // then
        assertAll(
                () -> assertThat(result.size()).isEqualTo(expected.size()),
                () -> assertThat(result).extracting(MatzipSummaryReponse::getName)
                                        .containsExactlyElementsOf(expected)
        );
    }

}

