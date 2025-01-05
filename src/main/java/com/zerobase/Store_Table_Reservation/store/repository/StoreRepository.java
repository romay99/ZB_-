package com.zerobase.Store_Table_Reservation.store.repository;

import com.zerobase.Store_Table_Reservation.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store,Long> {

    // 가게 이름 순으로 가게 데이터 받아오기
    @Query("SELECT s FROM Store s ORDER BY s.name ASC")
    List<Store> findAllOrderByStoreName();

    // 별점 높은순 정렬로 가게 데이터 받아오기
    @Query("SELECT s FROM Store s ORDER BY s.rating DESC")
    List<Store> findAllByRating();
}
