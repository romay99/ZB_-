package com.zerobase.Store_Table_Reservation.review.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewModifyDto {
    private Long reviewCode;
    private double rating;
    private String content;
}
