package com.zerobase.Store_Table_Reservation.member.dto.request;

import com.zerobase.Store_Table_Reservation.member.entity.Member;
import com.zerobase.Store_Table_Reservation.member.entity.MemberRole;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class MemberSignUpDto {
    private boolean partner;
    private String memberId;
    private String memberPassword;
    private String memberName;
    private String memberTel;

    /**
     * DTO --> Entity 변환 메서드
     */
    public static Member toEntity(MemberSignUpDto dto) {
        MemberRole partner = MemberRole.ROLE_USER;
        if (dto.partner) { // 파트너 가입인 경우에만 파트너 권한 추가
            partner = MemberRole.ROLE_PARTNER;
        }

        return Member.builder()
                .memberId(dto.getMemberId())
                .password(dto.getMemberPassword())
                .name(dto.getMemberName())
                .tel(dto.getMemberTel())
                .role(partner)
                .build();
    }
}
