package com.zerobase.Store_Table_Reservation.store.dto.request;

import com.zerobase.Store_Table_Reservation.member.entity.Member;
import com.zerobase.Store_Table_Reservation.store.entity.Store;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StoreUploadDto {
    private String storeName;
    private double latitude;
    private double longitude;
    private String description;

    public static Store toEntity(StoreUploadDto dto , Member member) {
        return Store.builder()
                .name(dto.getStoreName())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .description(dto.getDescription())
                .member(member)
                .build();
    }
}
