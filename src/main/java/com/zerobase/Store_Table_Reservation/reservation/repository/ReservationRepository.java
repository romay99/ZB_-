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

    // 가게 PK 로 가게를 찾고, 그 가게의 특정 날짜의 예약 리스트를 가져오는 메서드
    @Query("SELECT r FROM Reservation r WHERE r.store.code = :code AND r.reservationDate = :date")
    List<Reservation> findAllReservationByStoreAndToday(@Param("code") Long storeCode, @Param("date") LocalDate date);

    // 가게 PK, 예약날짜로 가게를 찾고, 유저의 ID 값으로 예약 내역을 불러오는 메서드
    @Query("SELECT r FROM Reservation r WHERE r.store.code = :code AND r.member.memberId = :memberId AND r.reservationDate = :date")
    List<Reservation> findByStoreCodeAndMemberId(@Param("code") Long storeCode, @Param("memberId") String memberId,@Param("date") LocalDate date);

    // 가게별 가게를 방문한 예약 내역들만 불러온다.
    @Query("SELECT r FROM Reservation r WHERE r.store.code = :code AND r.visited = TRUE")
    List<Reservation> findAllByStoreCode(@Param("code") Long code);
}
