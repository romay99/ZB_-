package com.zerobase.Store_Table_Reservation.reservation.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationSuccessResponse {
    private String storeName;
    private LocalDate date;
    private LocalTime time;

}
