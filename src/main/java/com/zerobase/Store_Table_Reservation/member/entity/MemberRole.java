package com.zerobase.Store_Table_Reservation.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MemberRole {
    MEMBER("ROLE_USER"),
    PARTNER("ROLE_PARTNER,ROLE_USER");

    private String value;
}
