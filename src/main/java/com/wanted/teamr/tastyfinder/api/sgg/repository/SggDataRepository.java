package com.wanted.teamr.tastyfinder.api.sgg.repository;

import com.wanted.teamr.tastyfinder.api.sgg.domain.SggData;
import org.springframework.data.repository.CrudRepository;

/**
 * 시군구 데이타 Redis 리포지토리
 */
public interface SggDataRepository extends CrudRepository<SggData, String> {
}
