package com.wanted.teamr.tastyfinder.api.matzip.repository;

import com.wanted.teamr.tastyfinder.api.matzip.domain.Matzip;
import com.wanted.teamr.tastyfinder.api.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MatzipRepository extends JpaRepository<Matzip, Long> {

    @Query("SELECT r FROM Review r WHERE r.matzip.Id = :matzipId ORDER BY r.modifiedAt DESC")
    List<Review> findReviewByMatzipIdOrderByModifiedAtDesc(@Param("matzipId") Long matzipId);

}
