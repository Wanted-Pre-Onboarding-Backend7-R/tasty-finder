package com.wanted.teamr.tastyfinder.api.matzip.repository;


import com.wanted.teamr.tastyfinder.api.matzip.domain.Matzip;
import com.wanted.teamr.tastyfinder.api.matzip.domain.MatzipListRetrieveCategory;

import java.util.List;

public interface MatzipRepositoryCustom {

    List<Matzip> retrieveMatzipList(MatzipListRetrieveCategory category);

}

