package com.wanted.teamr.tastyfinder.api.matzip;

import com.wanted.teamr.tastyfinder.api.matzip.domain.Matzip;
import com.wanted.teamr.tastyfinder.api.matzip.dto.MatzipSummaryReponse;
import com.wanted.teamr.tastyfinder.api.matzip.repository.MatzipRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

/**
 * matzip_raw 필드가 많아서 matzip.sql에 더미 데이터 설정 중
 * 에러가 많이 생겨서 해당 테스트 돌리면서 matzip.sql 오류 해결
 * 추후 더미 데이터 검증 용도로도 활용 가능
 */
@Transactional
@DisplayName("맛집 더미 데이터 확인 테스트")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MatzipDummyDataTest {

    @Autowired
    MatzipRepository matzipRepository;

    @BeforeAll
    static void beforeAll(@Autowired DataSource dataSource) {
        try (Connection conn = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("db/matzip.sql"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("MatzipRaw의 Unique Key bizNameLotNoAddr 변수의 값은 음식점 이름 + 소재지 지번 주소 값이다")
    void verifyMatzipBizNameLotNoAddr() {
        // given
        List<String> expected = Arrays.asList(
                "맛집 1소재지 지번 주소1", "맛집 2소재지 지번 주소2", "맛집 3소재지 지번 주소3",
                "맛집 4소재지 지번 주소4", "맛집 5소재지 지번 주소5", "맛집 6소재지 지번 주소6",
                "맛집 7소재지 지번 주소7", "맛집 8소재지 지번 주소8", "맛집 9소재지 지번 주소9",
                "맛집 10소재지 지번 주소10", "맛집 11소재지 지번 주소11", "맛집 12소재지 지번 주소12"
        );

        // when
        List<Matzip> result = matzipRepository.findAll();

        // then
        assertAll(
                () -> assertThat(result.size()).isEqualTo(expected.size()),
                () -> assertThat(result).extracting(m -> m.getMatzipRaw().getBizplcNm() +
                                                m.getMatzipRaw().getRefineLotnoAddr())
                                        .containsExactlyElementsOf(expected)
        );
    }
}
