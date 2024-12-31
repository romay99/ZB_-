package com.zerobase.Store_Table_Reservation.review.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReviewDetailResponse {
    private Long reviewCode;
    private String content;
    private double rating;
}
