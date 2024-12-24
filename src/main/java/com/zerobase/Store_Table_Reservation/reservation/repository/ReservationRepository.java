package com.zerobase.Store_Table_Reservation.reservation.repository;

import com.zerobase.Store_Table_Reservation.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {


}
