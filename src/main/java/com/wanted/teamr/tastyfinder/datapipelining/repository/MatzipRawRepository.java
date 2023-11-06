package com.wanted.teamr.tastyfinder.datapipelining.repository;

import com.wanted.teamr.tastyfinder.datapipelining.domain.MatzipRaw;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatzipRawRepository extends JpaRepository<MatzipRaw, Long>, CustomMatzipRawRepository {
}
