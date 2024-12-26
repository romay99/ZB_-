package com.zerobase.Store_Table_Reservation.store.repository;

import com.zerobase.Store_Table_Reservation.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store,Long> {

    List<Store> findAllByMemberMemberId(String memberId);

    // 가게 순으로 가게 데이터 받아오기
    @Query("select s from Store s order by s.name ASC")
    List<Store> findAllOrderByStoreName();
}
