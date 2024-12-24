package com.zerobase.Store_Table_Reservation.reservation.entity;

import com.zerobase.Store_Table_Reservation.member.entity.Member;
import com.zerobase.Store_Table_Reservation.store.entity.Store;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long code;

    private LocalDateTime dateTime;

    private boolean visited;

    private Store store;

    private Member member;
}
