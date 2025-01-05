package com.zerobase.Store_Table_Reservation.store.service;

import com.zerobase.Store_Table_Reservation.exception.customException.*;
import com.zerobase.Store_Table_Reservation.member.entity.Member;
import com.zerobase.Store_Table_Reservation.member.repository.MemberRepository;
import com.zerobase.Store_Table_Reservation.reservation.repository.ReservationRepository;
import com.zerobase.Store_Table_Reservation.store.dto.request.*;
import com.zerobase.Store_Table_Reservation.store.dto.response.StoreDetailDto;
import com.zerobase.Store_Table_Reservation.store.entity.Store;
import com.zerobase.Store_Table_Reservation.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;

    /**
     * 두 지점의 거리를 구하는 Haversine 공식
     */
    public double getDistanceFromUser(double lat1, double lat2, double long1, double long2) {
        final int R = 6371; // 지구 반지름 (Km)

        // 위도와 경도를 라디안으로 변환
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(long2 - long1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c; // 거리 계산 (Km 단위)
    }

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

    /**
     * 가게 상세정보 조회하는 메서드
     */
    public StoreDetailDto getStoreDetail(StoreDetailRequestDto dto) {
        // 가게 정보가 존재하지 않으면 예외 발생
        Store store = storeRepository.findById(dto.getStoreCode()).orElseThrow(
                () -> new StoreNotFoundException("존재하지 않는 가게 정보입니다."));

        double lat1 = dto.getLatitude();
        double long1 = dto.getLongitude();
        double lat2 = store.getLatitude();
        double long2 = store.getLongitude();

        // 응답을 위한 StoreDetailDto 생성 및 초기화
        return StoreDetailDto.builder()
                .storeCode(store.getCode())
                .storeName(store.getName())
                .storeDescription(store.getDescription())
                .storeRating(store.getRating())
                // Haversine 공식을 이용해 거리(km단위) 구하기
                .storeDistance(getDistanceFromUser(lat1, lat2, long1, long2))
                .build();
    }

    /**
     * 가게 정보들 받아오는 메서드 (이름순 정렬)
     */
    public List<StoreDetailDto> getStoreList(StoreDetailRequestDto dto) {
        // DB 에서 가게정보를 오름차순으로 받아온다.
        List<Store> storeList = storeRepository.findAllOrderByStoreName();

        List<StoreDetailDto> responseDtoList = storeList.stream().map(
                (data) -> StoreDetailDto.builder()
                        .storeName(data.getName())
                        .storeCode(data.getCode())
                        .storeDescription(data.getDescription())
                        .storeRating(data.getRating())
                        // 거리정보를 getDistanceFromUser() 함수를 통해 초기화 해준다.
                        .storeDistance(getDistanceFromUser(dto.getLatitude(), data.getLatitude(), dto.getLongitude(), data.getLongitude()))
                        .build()
        ).toList();

        return responseDtoList;
    }

    /**
     * 가게 정보 받아오는 메서드 (별점 순 정렬)
     */
    public List<StoreDetailDto> getStoreListByRating(StoreDetailRequestDto dto) {
        List<Store> storeList = storeRepository.findAllByRating();

        List<StoreDetailDto> responseDtoList = storeList.stream().map(
                (data) -> StoreDetailDto.builder()
                        .storeName(data.getName())
                        .storeCode(data.getCode())
                        .storeDescription(data.getDescription())
                        .storeRating(data.getRating())
                        // 거리정보를 getDistanceFromUser() 함수를 통해 초기화 해준다.
                        .storeDistance(getDistanceFromUser(dto.getLatitude(), data.getLatitude(), dto.getLongitude(), data.getLongitude()))
                        .build()
        ).toList();

        return responseDtoList;
    }
}
