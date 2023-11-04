package com.wanted.teamr.tastyfinder.datapipelining.domain;

import com.wanted.teamr.tastyfinder.datapipelining.dto.OpenAPIStoreRow;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = @Index(name = "matzip_raw_BIZ_NM_LOTNO_ADDR_idx", columnList = "BIZ_NM_LOTNO_ADDR", unique = true))
@Entity
public class MatzipRaw {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "BIZ_NM_LOTNO_ADDR", length = 200, nullable = false, updatable = false, unique = true)
    private String bizNameLotNoAddr;
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @Column(name = "SIGUN_NM", length = 10, nullable = false)
    private String sigunNm;
    @Column(name = "SIGUN_CD", length = 10)
    private String sigunCd;
    @Column(name = "BIZPLC_NM", length = 80, nullable = false)
    private String bizplcNm;
    @Column(name = "LICENSG_DE", length = 14)
    private String licensgDe;
    @Column(name = "BSN_STATE_NM", length = 20)
    private String bsnStateNm;
    @Column(name = "CLSBIZ_DE", length = 14)
    private String clsbizDe;
    @Column(name = "LOCPLC_AR", length = 20)
    private String locplcAr;
    @Column(name = "GRAD_FACLT_DIV_NM", length = 20)
    private String gradFacltDivNm;
    @Column(name = "MALE_ENFLPSN_CNT")
    private Integer maleEnflpsnCnt;
    @Column(name = "YY", length = 6)
    private String yy;
    @Column(name = "MULTI_USE_BIZESTBL_YN", length = 20)
    private String multiUseBizestblYn;
    @Column(name = "GRAD_DIV_NM", length = 20)
    private String gradDivNm;
    @Column(name = "TOT_FACLT_SCALE", length = 20)
    private String totFacltScale;
    @Column(name = "FEMALE_ENFLPSN_CNT")
    private Integer femaleEnflpsnCnt;
    @Column(name = "BSNSITE_CIRCUMFR_DIV_NM", length = 20)
    private String bsnsiteCircumfrDivNm;
    @Column(name = "SANITTN_INDUTYPE_NM", length = 20)
    private String sanittnIndutypeNm;
    @Column(name = "SANITTN_BIZCOND_NM", length = 20, nullable = false)
    private String sanittnBizcondNm;
    @Column(name = "TOT_EMPLY_CNT")
    private Integer totEmplyCnt;
    @Column(name = "REFINE_LOTNO_ADDR", length = 120, updatable = false, nullable = false)
    private String refineLotnoAddr;
    @Column(name = "REFINE_ROADNM_ADDR", length = 200)
    private String refineRoadnmAddr;
    @Column(name = "REFINE_ZIP_CD", length = 6)
    private String refineZipCd;
    @Column(name = "REFINE_WGS84_LOGT", length = 20, nullable = false)
    private String refineWgs84Logt;
    @Column(name = "REFINE_WGS84_LAT", length = 20, nullable = false)
    private String refineWgs84Lat;

    @Builder
    private MatzipRaw(String sigunNm, String sigunCd, String bizplcNm, String licensgDe, String bsnStateNm,
                      String clsbizDe, String locplcAr, String gradFacltDivNm, Integer maleEnflpsnCnt, String yy,
                      String multiUseBizestblYn, String gradDivNm, String totFacltScale, Integer femaleEnflpsnCnt,
                      String bsnsiteCircumfrDivNm, String sanittnIndutypeNm, String sanittnBizcondNm,
                      Integer totEmplyCnt, String refineLotnoAddr, String refineRoadnmAddr, String refineZipCd,
                      String refineWgs84Logt, String refineWgs84Lat) {
        this.bizNameLotNoAddr = bizplcNm + refineLotnoAddr;
        this.sigunNm = sigunNm;
        this.sigunCd = sigunCd;
        this.bizplcNm = bizplcNm;
        this.licensgDe = licensgDe;
        this.bsnStateNm = bsnStateNm;
        this.clsbizDe = clsbizDe;
        this.locplcAr = locplcAr;
        this.gradFacltDivNm = gradFacltDivNm;
        this.maleEnflpsnCnt = maleEnflpsnCnt;
        this.yy = yy;
        this.multiUseBizestblYn = multiUseBizestblYn;
        this.gradDivNm = gradDivNm;
        this.totFacltScale = totFacltScale;
        this.femaleEnflpsnCnt = femaleEnflpsnCnt;
        this.bsnsiteCircumfrDivNm = bsnsiteCircumfrDivNm;
        this.sanittnIndutypeNm = sanittnIndutypeNm;
        this.sanittnBizcondNm = sanittnBizcondNm;
        this.totEmplyCnt = totEmplyCnt;
        this.refineLotnoAddr = refineLotnoAddr;
        this.refineRoadnmAddr = refineRoadnmAddr;
        this.refineZipCd = refineZipCd;
        this.refineWgs84Logt = refineWgs84Logt;
        this.refineWgs84Lat = refineWgs84Lat;
    }

    public static MatzipRaw from(OpenAPIStoreRow row) {
        return builder()
                .sigunNm(row.getSigunNm())
                .sigunCd(row.getSigunCd())
                .bizplcNm(row.getBizplcNm())
                .licensgDe(row.getLicensgDe())
                .bsnStateNm(row.getBsnStateNm())
                .clsbizDe(row.getClsbizDe())
                .locplcAr(row.getLocplcAr())
                .gradFacltDivNm(row.getGradFacltDivNm())
                .maleEnflpsnCnt(row.getMaleEnflpsnCnt())
                .yy(row.getYy())
                .multiUseBizestblYn(row.getMultiUseBizestblYn())
                .gradDivNm(row.getGradDivNm())
                .totFacltScale(row.getTotFacltScale())
                .femaleEnflpsnCnt(row.getFemaleEnflpsnCnt())
                .bsnsiteCircumfrDivNm(row.getBsnsiteCircumfrDivNm())
                .sanittnIndutypeNm(row.getSanittnIndutypeNm())
                .sanittnBizcondNm(row.getSanittnBizcondNm())
                .totEmplyCnt(row.getTotEmplyCnt())
                .refineLotnoAddr(row.getRefineLotnoAddr())
                .refineRoadnmAddr(row.getRefineRoadnmAddr())
                .refineZipCd(row.getRefineZipCd())
                .refineWgs84Logt(row.getRefineWgs84Logt())
                .refineWgs84Lat(row.getRefineWgs84Lat())
                .build();
    }

}
