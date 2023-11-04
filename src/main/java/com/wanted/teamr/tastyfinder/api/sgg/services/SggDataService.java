package com.wanted.teamr.tastyfinder.api.sgg.services;

import com.wanted.teamr.tastyfinder.api.sgg.domain.SggData;
import com.wanted.teamr.tastyfinder.api.sgg.repository.SggDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 시군구 데이터 서비스
 */
@Service
@RequiredArgsConstructor
public class SggDataService {

    private final SggDataRepository sggDataRepository;

    @Transactional
    public void replaceAll(List<SggData> sggDataList) {
        sggDataRepository.deleteAll();
        sggDataRepository.saveAll(sggDataList);
    }

}
