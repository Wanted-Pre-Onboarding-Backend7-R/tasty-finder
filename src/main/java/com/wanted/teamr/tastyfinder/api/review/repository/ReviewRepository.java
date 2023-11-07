package com.wanted.teamr.tastyfinder.api.review.repository;

import com.wanted.teamr.tastyfinder.api.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
