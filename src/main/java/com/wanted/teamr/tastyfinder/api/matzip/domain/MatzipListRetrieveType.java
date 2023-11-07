package com.wanted.teamr.tastyfinder.api.matzip.domain;

import com.wanted.teamr.tastyfinder.api.matzip.dto.MatzipSummaryReponse;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 맛집 목록 조회 로직 다형성 구현할 때 if/else문으로 타입 구분 피하기 위해 enum 필드의 조회 로직 포함
 * <p>
 * 맛집 검색 목록 조회 시 query parameter "type"으로 입력받는 값 중 하나
 */
public enum MatzipListRetrieveType {
    // 거리 오름차순
    DISTANCE() {
        @Override
        public List<MatzipSummaryReponse> retrieve(List<Matzip> matzipList, Location requestLocation, double range) {
            return matzipList.stream()
                             .map(m -> MatzipSummaryReponse.of(m, m.calcDistanceFrom(requestLocation)))
                             .filter(m -> m.getDistance() <= range)
                             .sorted(Comparator.comparingDouble(MatzipSummaryReponse::getDistance))
                             .collect(Collectors.toList());
        }
    },
    // 평점 높은순
    AVG_RATING() {
        @Override
        public List<MatzipSummaryReponse> retrieve(List<Matzip> matzipList, Location requestLocation, double range) {
            return matzipList.stream()
                             .map(m -> MatzipSummaryReponse.of(m, m.calcDistanceFrom(requestLocation)))
                             .filter(m -> m.getDistance() <= range)
                             .sorted(Comparator.comparingDouble(MatzipSummaryReponse::getAvgRating).reversed())
                             .collect(Collectors.toList());
        }
    };

    public abstract List<MatzipSummaryReponse> retrieve(List<Matzip> matzipList, Location requestLocation, double range);

}

