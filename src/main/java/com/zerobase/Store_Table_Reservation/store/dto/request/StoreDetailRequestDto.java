package com.zerobase.Store_Table_Reservation.store.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreDetailRequestDto {
    private long storeCode;
    private double latitude; // 사용자의 경도
    private double longitude; // 사용자의 위도
}
