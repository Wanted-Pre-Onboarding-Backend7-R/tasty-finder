package com.wanted.teamr.tastyfinder.api.matzip.dto;

import com.wanted.teamr.tastyfinder.api.matzip.domain.MatzipListRetrieveCategory;
import com.wanted.teamr.tastyfinder.api.matzip.domain.MatzipListRetrieveType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MatzipListRetrieveRequest {

    private String lat;

    private String lon;

    private String range;

    private MatzipListRetrieveType type;

    private MatzipListRetrieveCategory category;

    @Builder
    public MatzipListRetrieveRequest(String lat, String lon, String range, MatzipListRetrieveType type,
                                     MatzipListRetrieveCategory category) {
        this.lat = lat;
        this.lon = lon;
        this.range = range;
        this.type = type == null ? MatzipListRetrieveType.DISTANCE : type;
        this.category = category == null ? MatzipListRetrieveCategory.ALL : category;
    }
}

