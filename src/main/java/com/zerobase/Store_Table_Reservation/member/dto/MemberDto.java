package com.zerobase.Store_Table_Reservation.member.dto;

import com.zerobase.Store_Table_Reservation.member.entity.MemberRole;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MemberDto {
    private String memberId;
    private List<MemberRole> role;
}
