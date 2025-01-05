package com.zerobase.Store_Table_Reservation.review.repository;

import com.zerobase.Store_Table_Reservation.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {

    // 해당 가게의 리뷰들 불러오기
    @Query("SELECT r FROM Review r WHERE r.storeCode = :code")
    List<Review> findAllByStoreCode(@Param("code")Long code);

}
