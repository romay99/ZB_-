package com.zerobase.Store_Table_Reservation.reservation.repository;

import com.zerobase.Store_Table_Reservation.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT r FROM Reservation r WHERE r.store.code = :code AND r.reservationDate = :date")
    List<Reservation> findAllReservationByStoreAndToday(@Param("code") Long storeCode, @Param("date") LocalDate date);



}
