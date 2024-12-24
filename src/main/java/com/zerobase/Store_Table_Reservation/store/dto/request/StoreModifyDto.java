package com.zerobase.Store_Table_Reservation.store.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreModifyDto {
    private long storeCode;
    private String storeName;
    private double latitude;
    private double longitude;
    private String description;
}
