package com.zerobase.Store_Table_Reservation.store.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
public class StoreReserveDto {
    private long storeCode;
    private LocalDate date;
    private LocalTime time;

}
