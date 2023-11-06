package com.wanted.teamr.tastyfinder.datapipelining.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OpenAPIKey {

    public static String CAFE;
    public static String JAPANESE_FOOD;
    public static String CHINESE_FOOD;

    @Value("${open-api-key.cafe}")
    public void setCAFE(String CAFE) {
        OpenAPIKey.CAFE = CAFE;
    }

    @Value("${open-api-key.japanese-food}")
    public void setJAPANESE_FOOD(String JAPANESE_FOOD) {
        OpenAPIKey.JAPANESE_FOOD = JAPANESE_FOOD;
    }

    @Value("${open-api-key.chinese-food}")
    public void setCHINESE_FOOD(String CHINESE_FOOD) {
        OpenAPIKey.CHINESE_FOOD = CHINESE_FOOD;
    }

}
