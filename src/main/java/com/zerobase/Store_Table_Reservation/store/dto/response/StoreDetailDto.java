package com.zerobase.Store_Table_Reservation.store.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StoreDetailDto {
    private long storeCode;
    private String storeName;
    private double storeDistance;
    private String storeDescription;
    private double storeRating;
}
