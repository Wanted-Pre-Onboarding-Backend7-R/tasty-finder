package com.wanted.teamr.tastyfinder.datapipelining.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Generated;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
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
