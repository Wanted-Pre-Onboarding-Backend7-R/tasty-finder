package com.wanted.teamr.tastyfinder.api.matzip.repository;

import com.wanted.teamr.tastyfinder.api.matzip.domain.Matzip;
import com.wanted.teamr.tastyfinder.api.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MatzipRepository extends JpaRepository<Matzip, Long>, MatzipRepositoryCustom {

    @Query("SELECT r FROM Review r WHERE r.matzip.id = :matzipId ORDER BY r.updatedAt DESC")
    List<Review> findReviewByMatzipIdOrderByModifiedAtDesc(@Param("matzipId") Long matzipId);

}
