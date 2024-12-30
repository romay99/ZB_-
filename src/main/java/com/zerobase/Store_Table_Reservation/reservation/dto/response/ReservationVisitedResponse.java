package com.zerobase.Store_Table_Reservation.reservation.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
public class ReservationVisitedResponse {
    private String storeName;
    private String memberId;
    private LocalDate reservationDate;
    private LocalTime reservationTime;
}
