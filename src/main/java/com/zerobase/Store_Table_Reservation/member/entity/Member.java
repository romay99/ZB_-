package com.zerobase.Store_Table_Reservation.member.entity;

import jakarta.persistence.*;

@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long code;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    private String Id;

    private String password;

    private String name;

    private String tel;

}
