package com.wanted.teamr.tastyfinder.api.matzip.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.wanted.teamr.tastyfinder.api.matzip.dto.MatzipSummaryResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * 맛집 목록 조회 로직 다형성 구현할 때 if/else문으로 타입 구분 피하기 위해 enum 필드의 조회 로직 포함
 * <p>
 * 맛집 검색 목록 조회 시 query parameter "type"으로 입력받는 값 중 하나
 */
public enum MatzipListRetrieveType {
    // 거리 오름차순
    DISTANCE() {
        @Override
        public List<MatzipSummaryResponse> retrieve(List<Matzip> matzipList, Location requestLocation, double range,
                                                    int page, int pageSize) {
            List<MatzipSummaryResponse> matzips
                    = matzipList.stream()
                                .map(m -> MatzipSummaryResponse.of(m, requestLocation))
                                .filter(m -> m.getDistance() <= range)
                                .sorted(Comparator.comparingDouble(MatzipSummaryResponse::getDistance))
                                .toList();
            return paging(matzips, page, pageSize);
        }
    },
    // 평점 높은순
    AVG_RATING() {
        @Override
        public List<MatzipSummaryResponse> retrieve(List<Matzip> matzipList, Location requestLocation, double range,
                                                    int page, int pageSize) {
            List<MatzipSummaryResponse> matzips
                    = matzipList.stream()
                                .map(m -> MatzipSummaryResponse.of(m, requestLocation))
                                .filter(m -> m.getDistance() <= range)
                                .sorted(Comparator.comparingDouble(MatzipSummaryResponse::getAvgRating).reversed())
                                .toList();
            return paging(matzips, page, pageSize);
        }
    };

    public abstract List<MatzipSummaryResponse> retrieve(List<Matzip> matzipList, Location requestLocation, double range,
                                                         int page, int pageSize);

    private static List<MatzipSummaryResponse> paging(List<MatzipSummaryResponse> matzips, int page, int pageSize) {
        // page 1부터 시작하기로 정해서 계산은 -1 처리
        int fromIndex = (page - 1) * pageSize;
        int toIndex = page * pageSize;
        if (fromIndex >= matzips.size()) {
            return Collections.EMPTY_LIST;
        }

        if (toIndex > matzips.size()) {
            toIndex = matzips.size();
        }

        // subList(0, 15) -> index 0부터 14까지
        // subList는 기존 list의 view일 뿐이라 기존 list가 gc 대상이 되지않는 이슈가 있다고 함
        // 그래서 새로운 복사본 생성해서 반환하도록 설정
        return new ArrayList<>(matzips.subList(fromIndex, toIndex));
    }

}

