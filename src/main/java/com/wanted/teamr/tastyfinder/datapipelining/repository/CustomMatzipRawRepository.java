package com.wanted.teamr.tastyfinder.datapipelining.repository;

import com.wanted.teamr.tastyfinder.datapipelining.domain.MatzipRaw;

import java.util.List;

public interface CustomMatzipRawRepository {

    void insertBatch(List<MatzipRaw> matzipRaws);

}
