package com.zerobase.Store_Table_Reservation.store.service;

import com.zerobase.Store_Table_Reservation.exception.customException.MemberNotFoundException;
import com.zerobase.Store_Table_Reservation.exception.customException.StoreMemberNotMatchException;
import com.zerobase.Store_Table_Reservation.exception.customException.StoreNotFoundException;
import com.zerobase.Store_Table_Reservation.member.entity.Member;
import com.zerobase.Store_Table_Reservation.member.repository.MemberRepository;
import com.zerobase.Store_Table_Reservation.store.dto.request.StoreModifyDto;
import com.zerobase.Store_Table_Reservation.store.dto.request.StoreUploadDto;
import com.zerobase.Store_Table_Reservation.store.entity.Store;
import com.zerobase.Store_Table_Reservation.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    /**
     * 가게 수정하는 메서드
     */
    public Store modifyStore(StoreModifyDto dto, String username) {
        // 가게 정보가 존재하지 않는다면 예외 발생
        Store store = storeRepository.findById(dto.getStoreCode()).orElseThrow(
                () -> new StoreNotFoundException("가게정보가 존재하지 않습니다.")
        );

        // 회원 정보가 존재하지 않는다면 예외 발생
        Member member = memberRepository.findByMemberId(username).orElseThrow(
                () -> new MemberNotFoundException("존재하지 않는 사용자입니다.")
        );

        // 요청한 회원의 정보와 등록되어있는 가게의 점장정보가 다르면 예외 발생
        if (member.getCode() != store.getMember().getCode()) {
            throw new StoreMemberNotMatchException("본인의 가게만 수정 가능합니다.");
        }
        // DB 에서 찾아온 Entity 의 정보를 수정
        store.setName(dto.getStoreName());
        store.setLongitude(dto.getLongitude());
        store.setLatitude(dto.getLatitude());
        store.setDescription(dto.getDescription());
        return storeRepository.save(store);
    }

    /**
     * 가게 삭제하는 메서드
     */
    public void deleteStore(long storeCode, String memberId) {
        // 가게 정보가 존재하지 않는다면 예외 발생
        Store store = storeRepository.findById(storeCode).orElseThrow(
                () -> new StoreNotFoundException("가게정보가 존재하지 않습니다.")
        );

        // 회원 정보가 존재하지 않는다면 예외 발생
        Member member = memberRepository.findByMemberId(memberId).orElseThrow(
                () -> new MemberNotFoundException("존재하지 않는 사용자입니다.")
        );

        // 요청한 회원의 정보와 등록되어있는 가게의 점장정보가 다르면 예외 발생
        if (member.getCode() != store.getMember().getCode()) {
            throw new StoreMemberNotMatchException("본인의 가게만 삭제 가능합니다.");
        }
        // 파라미터로 받은 PK 값으로 삭제
        storeRepository.deleteById(storeCode);
    }
}
