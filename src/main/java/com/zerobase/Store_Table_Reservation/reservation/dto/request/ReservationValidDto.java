package com.zerobase.Store_Table_Reservation.reservation.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationValidDto {
    private Long reservationCode;
    private boolean makeValid;
}
