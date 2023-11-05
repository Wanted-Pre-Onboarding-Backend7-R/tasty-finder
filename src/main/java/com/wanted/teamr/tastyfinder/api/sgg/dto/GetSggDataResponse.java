package com.wanted.teamr.tastyfinder.api.sgg.dto;

import com.wanted.teamr.tastyfinder.api.sgg.domain.SggData;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GetSggDataResponse {

    private final String id;
    private final String dosi;
    private final String sgg;
    private final String lon;
    private final String lat;

    @Builder
    private GetSggDataResponse(String id, String dosi, String sgg, String lon, String lat) {
        this.id = id;
        this.dosi = dosi;
        this.sgg = sgg;
        this.lon = lon;
        this.lat = lat;
    }

    public static GetSggDataResponse from(SggData sggData) {
        return GetSggDataResponse.builder()
                .id(sggData.getId())
                .dosi(sggData.getDosi())
                .sgg(sggData.getSgg())
                .lon(sggData.getLon())
                .lat(sggData.getLat())
                .build();
    }
}
