package com.wanted.teamr.tastyfinder.api.sgg.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash("SggData")
public class SggData {

    @Id
    private final String id;
    private final String dosi;
    private final String sgg;
    private final String longitude;
    private final String latitude;

    @Builder
    public SggData(String id, String dosi, String sgg, String longitude, String latitude) {
        this.id = id;
        this.dosi = dosi;
        this.sgg = sgg;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public static SggData from(String dosi, String sgg, String longitude, String latitude) {
        String id = dosi + sgg;
        return SggData.builder()
                .id(id)
                .dosi(dosi)
                .sgg(sgg)
                .longitude(longitude)
                .latitude(latitude)
                .build();
    }

}
