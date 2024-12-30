package com.zerobase.Store_Table_Reservation.reservation.dto.response;

import com.zerobase.Store_Table_Reservation.reservation.entity.Reservation;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
public class ReservationDetailResponse {
    private LocalDate date;
    private LocalTime time;
    private String memberId;
    private String memberTel;

    public static ReservationDetailResponse toDto(Reservation reservation) {
        return ReservationDetailResponse.builder()
                .date(reservation.getReservationDate())
                .time(reservation.getReservationTime())
                .memberTel(reservation.getMember().getTel())
                .memberId(reservation.getMember().getMemberId())
                .build();
    }
}
