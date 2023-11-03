package com.wanted.teamr.tastyfinder.datapipelining.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.annotation.Generated;
import lombok.Data;

@Data
@JsonPropertyOrder({
        "SIGUN_NM",
        "SIGUN_CD",
        "BIZPLC_NM",
        "LICENSG_DE",
        "BSN_STATE_NM",
        "CLSBIZ_DE",
        "LOCPLC_AR",
        "GRAD_FACLT_DIV_NM",
        "MALE_ENFLPSN_CNT",
        "YY",
        "MULTI_USE_BIZESTBL_YN",
        "GRAD_DIV_NM",
        "TOT_FACLT_SCALE",
        "FEMALE_ENFLPSN_CNT",
        "BSNSITE_CIRCUMFR_DIV_NM",
        "SANITTN_INDUTYPE_NM",
        "SANITTN_BIZCOND_NM",
        "TOT_EMPLY_CNT",
        "REFINE_LOTNO_ADDR",
        "REFINE_ROADNM_ADDR",
        "REFINE_ZIP_CD",
        "REFINE_WGS84_LOGT",
        "REFINE_WGS84_LAT"
})
@Generated("jsonschema2pojo")
public class OpenAPIStoreRow {

    @JsonProperty("SIGUN_NM")
    private String sigunNm;
    @JsonProperty("SIGUN_CD")
    private String sigunCd;
    @JsonProperty("BIZPLC_NM")
    private String bizplcNm;
    @JsonProperty("LICENSG_DE")
    private String licensgDe;
    @JsonProperty("BSN_STATE_NM")
    private String bsnStateNm;
    @JsonProperty("CLSBIZ_DE")
    private String clsbizDe;
    @JsonProperty("LOCPLC_AR")
    private String locplcAr;
    @JsonProperty("GRAD_FACLT_DIV_NM")
    private String gradFacltDivNm;
    @JsonProperty("MALE_ENFLPSN_CNT")
    private Integer maleEnflpsnCnt;
    @JsonProperty("YY")
    private String yy;
    @JsonProperty("MULTI_USE_BIZESTBL_YN")
    private String multiUseBizestblYn;
    @JsonProperty("GRAD_DIV_NM")
    private String gradDivNm;
    @JsonProperty("TOT_FACLT_SCALE")
    private String totFacltScale;
    @JsonProperty("FEMALE_ENFLPSN_CNT")
    private Integer femaleEnflpsnCnt;
    @JsonProperty("BSNSITE_CIRCUMFR_DIV_NM")
    private String bsnsiteCircumfrDivNm;
    @JsonProperty("SANITTN_INDUTYPE_NM")
    private String sanittnIndutypeNm;
    @JsonProperty("SANITTN_BIZCOND_NM")
    private String sanittnBizcondNm;
    @JsonProperty("TOT_EMPLY_CNT")
    private Integer totEmplyCnt;
    @JsonProperty("REFINE_LOTNO_ADDR")
    private String refineLotnoAddr;
    @JsonProperty("REFINE_ROADNM_ADDR")
    private String refineRoadnmAddr;
    @JsonProperty("REFINE_ZIP_CD")
    private String refineZipCd;
    @JsonProperty("REFINE_WGS84_LOGT")
    private String refineWgs84Logt;
    @JsonProperty("REFINE_WGS84_LAT")
    private String refineWgs84Lat;

}
