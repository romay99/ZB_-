package com.zerobase.Store_Table_Reservation.store.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class StoreReserveDto {
    private long storeCode;
    private LocalDateTime dateTime;

}
