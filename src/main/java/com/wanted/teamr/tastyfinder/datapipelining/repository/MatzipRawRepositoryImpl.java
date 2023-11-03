package com.wanted.teamr.tastyfinder.datapipelining.repository;

import com.wanted.teamr.tastyfinder.datapipelining.domain.MatzipRaw;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import javax.sql.DataSource;
import java.util.List;

public class MatzipRawRepositoryImpl implements CustomMatzipRawRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public MatzipRawRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void insertBatch(List<MatzipRaw> matzipRaws) {
        SqlParameterSource[] paramSources = matzipRaws.stream()
                .map(BeanPropertySqlParameterSource::new)
                .toArray(SqlParameterSource[]::new);
        final String sql =
                "insert into matzip_raw (biz_nm_lotno_addr, sigun_nm, sigun_cd, bizplc_nm, licensg_de, " +
                    "bsn_state_nm, clsbiz_de, locplc_ar, grad_faclt_div_nm, male_enflpsn_cnt, " +
                    "yy, multi_use_bizestbl_yn, grad_div_nm, tot_faclt_scale, female_enflpsn_cnt, " +
                    "bsnsite_circumfr_div_nm, sanittn_indutype_nm, sanittn_bizcond_nm, tot_emply_cnt, refine_lotno_addr, " +
                    "refine_roadnm_addr, refine_zip_cd, refine_wgs84_logt, refine_wgs84_lat, created_at, updated_at) " +
                "values (:bizNameLotNoAddr, :sigunNm, :sigunCd, :bizplcNm, :licensgDe, " +
                    ":bsnStateNm, :clsbizDe, :locplcAr, :gradFacltDivNm, :maleEnflpsnCnt, " +
                    ":yy, :multiUseBizestblYn, :gradDivNm, :totFacltScale, :femaleEnflpsnCnt, " +
                    ":bsnsiteCircumfrDivNm, :sanittnIndutypeNm, :sanittnBizcondNm, :totEmplyCnt, :refineLotnoAddr, " +
                    ":refineRoadnmAddr, :refineZipCd, :refineWgs84Logt, :refineWgs84Lat, CURRENT_TIMESTAMP, current_time) as new " +
                "on duplicate key update " +
                    "bsn_state_nm = new.bsn_state_nm, " +
                    "clsbiz_de = new.clsbiz_de, " +
                    "male_enflpsn_cnt = new.male_enflpsn_cnt, " +
                    "female_enflpsn_cnt = new.female_enflpsn_cnt, " +
                    "tot_emply_cnt = new.tot_emply_cnt, " +
                    "refine_roadnm_addr = new.refine_roadnm_addr, " +
                    "refine_wgs84_logt = new.refine_wgs84_logt, " +
                    "refine_wgs84_lat = new.refine_wgs84_lat";
        jdbcTemplate.batchUpdate(sql, paramSources);
    }

}
