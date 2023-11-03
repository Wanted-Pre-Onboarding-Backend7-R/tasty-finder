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
    private final String lon;
    private final String lat;

    @Builder
    public SggData(String id, String dosi, String sgg, String lon, String lat) {
        this.id = id;
        this.dosi = dosi;
        this.sgg = sgg;
        this.lon = lon;
        this.lat = lat;
    }

    public static SggData from(String dosi, String sgg, String longitude, String latitude) {
        return SggData.builder()
                .id(makeId(dosi, sgg))
                .dosi(dosi)
                .sgg(sgg)
                .lon(longitude)
                .lat(latitude)
                .build();
    }

    /**
     * 도시와 시군구를 이용해 redis id 만들고 리턴. 시군구 단독으로 유니크한 id만들기 불가하므로 도시도 포함.
     * 시군구가 겹치는 예시로 "부산시 남구", "울산시 남구"가 있다.
     *
     * @param dosi
     * @param sgg
     * @return redis id
     */
    private static String makeId(String dosi, String sgg) {
        return dosi + ":" + sgg;
    }

}
