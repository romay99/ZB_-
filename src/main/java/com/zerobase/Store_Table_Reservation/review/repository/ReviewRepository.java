package com.zerobase.Store_Table_Reservation.review.repository;

import com.zerobase.Store_Table_Reservation.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {
}
