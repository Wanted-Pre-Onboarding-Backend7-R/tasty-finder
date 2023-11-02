package com.wanted.teamr.tastyfinder.api.matzip.dto;

import com.wanted.teamr.tastyfinder.api.matzip.domain.MatzipListRetrieveType;
import lombok.Builder;
import lombok.Getter;

// TODO: request dto validation

@Getter
public class MatzipListRetrieveRequest {

    private String lat;

    private String lon;

    private String range;

    private MatzipListRetrieveType type;

    @Builder
    private MatzipListRetrieveRequest(String lat, String lon, String range, MatzipListRetrieveType type) {
        this.lat = lat;
        this.lon = lon;
        this.range = range;
        this.type = type;
    }
}

