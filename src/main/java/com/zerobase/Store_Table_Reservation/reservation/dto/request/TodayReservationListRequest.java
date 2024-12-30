package com.zerobase.Store_Table_Reservation.reservation.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TodayReservationListRequest {
    private Long storeCode;
    private LocalDate date;
}
