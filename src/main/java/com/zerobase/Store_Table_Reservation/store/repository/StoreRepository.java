package com.zerobase.Store_Table_Reservation.store.repository;

import com.zerobase.Store_Table_Reservation.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store,Long> {

    List<Store> findAllByMemberMemberId(String memberId);
}
