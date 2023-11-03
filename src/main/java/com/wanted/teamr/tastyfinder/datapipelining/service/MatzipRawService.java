package com.wanted.teamr.tastyfinder.datapipelining.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.teamr.tastyfinder.datapipelining.constant.OpenAPIStoreType;
import com.wanted.teamr.tastyfinder.datapipelining.domain.MatzipRaw;
import com.wanted.teamr.tastyfinder.datapipelining.dto.OpenAPIStoreResponse;
import com.wanted.teamr.tastyfinder.datapipelining.dto.OpenAPIStoreRow;
import com.wanted.teamr.tastyfinder.datapipelining.repository.MatzipRawRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class MatzipRawService {

    private static final String urlFormatted =
            "https://openapi.gg.go.kr/%s?" +
                    "KEY=%s&" +
                    "Type=json&" +
                    "pIndex=%d&" +
                    "pSize=1000";

    private final MatzipRawRepository matzipRawRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;

    @Scheduled(cron = "0 0 5 * * *")
    @Transactional
    public void fetchAndSave() throws JsonProcessingException {
        int i = 1;
        int totalRowCount = 0;
        int totalFilteredRowCount = 0;
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>시작<<<<<<<<<<<<<<<<<<<<<<<<");
        log.info("ALL              start");
        long startTime = System.currentTimeMillis();
        for (OpenAPIStoreType type : OpenAPIStoreType.values()) {
            log.info("{}. {} start", i, "%-13s".formatted(type.name()));
            int typeRowCount = 0;
            int typeFilteredRowCount = 0;
            for (int pageIndex = 1; ; pageIndex++) {
                String url = urlFormatted.formatted(type.getDomain(), type.getKey(), pageIndex);
                String body = restTemplate.getForObject(url, String.class);
                OpenAPIStoreResponse openAPIResponse = mapper.readValue(body, OpenAPIStoreResponse.class);
//                OpenAPIStoreResponse openAPIResponse = restTemplate.getForObject(url, OpenAPIStoreResponse.class); // 500 Internal Server Error
                if (Objects.requireNonNull(openAPIResponse).getResult() != null) {
                    break;
                }
                List<OpenAPIStoreRow> rowsUnfiltered = openAPIResponse.getRows();
                typeRowCount += rowsUnfiltered.size();
                totalRowCount += rowsUnfiltered.size();
                List<MatzipRaw> matzipRaws = rowsUnfiltered.stream()
                        .filter(row -> row.getRefineLotnoAddr() != null
                                && row.getBizplcNm() != null
                                && row.getRefineWgs84Logt() != null
                                && row.getRefineWgs84Lat() != null
                                && row.getSanittnBizcondNm() != null)
                        .map(MatzipRaw::from)
                        .toList();
                typeFilteredRowCount += matzipRaws.size();
                totalFilteredRowCount += matzipRaws.size();
                matzipRawRepository.insertBatch(matzipRaws);
            }
            log.info("{}. {} typeRowCount        : {}", i, "%-13s".formatted(type.name()), typeRowCount);
            log.info("{}. {} typeFilteredRowCount: {}", i, "%-13s".formatted(type.name()), typeFilteredRowCount);
            log.info("{}. {} end", i, "%-13s".formatted(type.name()));
            i++;
        }
        log.info("ALL              end");
        log.info("totalRowCount: {}", totalRowCount);
        log.info("totalFilteredRowCount: {}", totalFilteredRowCount);
        log.info("Fetch &  Bulk upsert time: {} ms", System.currentTimeMillis() - startTime);
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>종료<<<<<<<<<<<<<<<<<<<<<<<<");
    }

}
