package com.zerobase.Store_Table_Reservation.member.dto.request;

import com.zerobase.Store_Table_Reservation.member.entity.MemberRole;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class MemberDto {
    private String memberId;
    private MemberRole role;
}
