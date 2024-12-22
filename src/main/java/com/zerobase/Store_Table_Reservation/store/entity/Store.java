package com.zerobase.Store_Table_Reservation.store.entity;

import com.zerobase.Store_Table_Reservation.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long code;

    private String name;

    // 가게 위도
    private double latitude;

    // 가게 경도
    private double longitude;

    private String description;

    @ManyToOne
    private Member member;
}
