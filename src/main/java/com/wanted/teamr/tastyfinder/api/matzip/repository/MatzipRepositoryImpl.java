package com.wanted.teamr.tastyfinder.api.matzip.repository;


import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanted.teamr.tastyfinder.api.matzip.domain.Matzip;
import com.wanted.teamr.tastyfinder.api.matzip.domain.MatzipListRetrieveCategory;

import java.util.List;

import static com.wanted.teamr.tastyfinder.api.matzip.domain.QMatzip.matzip;
import static com.wanted.teamr.tastyfinder.datapipelining.domain.QMatzipRaw.matzipRaw;


public class MatzipRepositoryImpl implements MatzipRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public MatzipRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
    this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<Matzip> retrieveMatzipList(MatzipListRetrieveCategory category) {
        return jpaQueryFactory.selectFrom(matzip)
                .innerJoin(matzip.matzipRaw, matzipRaw).on(isEqualCategory(category))
                .fetch();
    }

    private BooleanExpression isEqualCategory(MatzipListRetrieveCategory category) {
        if (category == null || category == MatzipListRetrieveCategory.ALL) {
            return matzipRaw.sanittnBizcondNm.isNotNull();
        }

        return matzipRaw.sanittnBizcondNm.eq(category.getRawValue());
    }
}

