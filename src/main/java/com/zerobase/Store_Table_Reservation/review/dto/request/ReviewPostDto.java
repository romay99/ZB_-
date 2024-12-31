package com.zerobase.Store_Table_Reservation.review.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReviewPostDto {
    private long reservationCode;
    private double rating;
    private String content;
}
