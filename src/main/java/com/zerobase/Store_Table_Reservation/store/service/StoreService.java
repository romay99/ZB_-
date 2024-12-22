package com.zerobase.Store_Table_Reservation.store.service;

import com.zerobase.Store_Table_Reservation.exception.customException.MemberNotFoundException;
import com.zerobase.Store_Table_Reservation.member.entity.Member;
import com.zerobase.Store_Table_Reservation.member.repository.MemberRepository;
import com.zerobase.Store_Table_Reservation.store.dto.request.StoreUploadDto;
import com.zerobase.Store_Table_Reservation.store.entity.Store;
import com.zerobase.Store_Table_Reservation.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;

    /**
     * 가게 업로드 하는 메서드
     */
    public Store uploadStore(StoreUploadDto dto, String memberId) {
        // 사용자가 존재하지 않는다면 예외 발생
        Member partner = memberRepository.findByMemberId(memberId).orElseThrow(
                () -> new MemberNotFoundException("존재하지 않는 사용자입니다.")
        );

        // 멤버의 정보를 담는 partner 변수도 같이 전달해준다.
        Store store = StoreUploadDto.toEntity(dto, partner);
        return storeRepository.save(store);
    }
}
