package com.wanted.teamr.tastyfinder.api.sgg.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.teamr.tastyfinder.api.sgg.dto.GetSggDataResponse;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
class SggDataControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @DisplayName("GET /api/sggdata API를 통해 Redis에 적재된 SggData 리스트를 얻을 수 있다.")
    @Test
    void getSggDataList() throws Exception {
        // when
        String content = mockMvc.perform(get("/api/sggdata"))
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        // then
        List<GetSggDataResponse> response = objectMapper.readValue(content, new TypeReference<>() {
        });
        assertThat(response).hasSize(3);
        assertThat(response).extracting("id", "dosi", "sgg", "lon", "lat")
                .containsExactlyInAnyOrder(
                        Tuple.tuple("강원:강릉시", "강원", "강릉시", "128.8784972", "37.74913611"),
                        Tuple.tuple("강원:고성군", "강원", "고성군", "128.4701639", "38.37796111"),
                        Tuple.tuple("강원:동해시", "강원", "동해시", "129.1166333", "37.52193056")
                );
    }

}
