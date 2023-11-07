package com.wanted.teamr.tastyfinder.api.matzip.domain;

import lombok.Getter;

@Getter
public enum MatzipListRetrieveCategory {
    // default 값, 미 입력 시 모든 음식점 종류 조회
    ALL("all"),
    CAFE("까페"),
    JPN("일식"),
    CHN("중국식");

    /**
     * db raw data와 대응되는 값
     */
    private final String rawValue;

    MatzipListRetrieveCategory(String rawValue) {
        this.rawValue = rawValue;
    }
}

