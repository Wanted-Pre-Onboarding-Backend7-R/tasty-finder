package com.wanted.teamr.tastyfinder.datapipelining.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OpenAPIStoreType {

    CAFE(OpenAPIKey.CAFE, "Genrestrtcate"),
    JAPANESE_FOOD(OpenAPIKey.JAPANESE_FOOD, "Genrestrtjpnfood"),
    CHINESE_FOOD(OpenAPIKey.CHINESE_FOOD, "Genrestrtchifood"),
    ;

    private final String key;
    private final String domain;

}
