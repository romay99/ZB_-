package com.zerobase.Store_Table_Reservation.reservation.entity;

import com.zerobase.Store_Table_Reservation.member.entity.Member;
import com.zerobase.Store_Table_Reservation.store.entity.Store;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long code;

    private LocalTime reservationTime;

    private LocalDate reservationDate;

    private boolean visited;

    @ManyToOne
    private Store store;

    @ManyToOne
    private Member member;

    private boolean valid;
}
