package com.zerobase.Store_Table_Reservation.store.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
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
}
