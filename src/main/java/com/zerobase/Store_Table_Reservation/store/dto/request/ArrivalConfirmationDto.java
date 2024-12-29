package com.zerobase.Store_Table_Reservation.store.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class ArrivalConfirmationDto {
    private Long storeCode;
    private String memberId;
    private String memberTel;
    private LocalTime arrivalTime;
}
