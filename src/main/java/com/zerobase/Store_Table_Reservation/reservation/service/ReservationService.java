package com.zerobase.Store_Table_Reservation.reservation.service;

import com.zerobase.Store_Table_Reservation.exception.customException.*;
import com.zerobase.Store_Table_Reservation.member.entity.Member;
import com.zerobase.Store_Table_Reservation.member.repository.MemberRepository;
import com.zerobase.Store_Table_Reservation.reservation.dto.request.TodayReservationListRequest;
import com.zerobase.Store_Table_Reservation.reservation.dto.response.ReservationDetailResponse;
import com.zerobase.Store_Table_Reservation.reservation.dto.response.ReservationSuccessResponse;
import com.zerobase.Store_Table_Reservation.reservation.dto.response.ReservationVisitedResponse;
import com.zerobase.Store_Table_Reservation.reservation.entity.Reservation;
import com.zerobase.Store_Table_Reservation.reservation.repository.ReservationRepository;
import com.zerobase.Store_Table_Reservation.store.dto.request.ArrivalConfirmationDto;
import com.zerobase.Store_Table_Reservation.store.dto.request.StoreReserveDto;
import com.zerobase.Store_Table_Reservation.store.entity.Store;
import com.zerobase.Store_Table_Reservation.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;

    /**
     * 가게 예약하는 메서드
     */
    public ReservationSuccessResponse reserveStore(StoreReserveDto dto, String memberId) {
        // 선택한 가게의 당일 예약 데이터들 가져오기
        List<Reservation> todayReservationList = reservationRepository.findAllReservationByStoreAndToday(dto.getStoreCode(),dto.getDate());

        for (Reservation r : todayReservationList) {
            // 당일 예약중 겹치는 시간이 있다면 예외 발생
            if (r.getReservationTime().getHour() == dto.getTime().getHour()) {
                throw new AlreadyReservedException("선택한 시간은 예약이 불가능합니다.");
            }
        }

        // Token 에서 추출한 Member 의 ID 로 Member Entity 가져오기
        Member member = memberRepository.findByMemberId(memberId).orElseThrow(
                () -> new MemberNotFoundException("존재하지 않는 사용자입니다.")
        );

        // Store Entity 가져오기
        Store store = storeRepository.findById(dto.getStoreCode()).orElseThrow(
                () -> new StoreNotFoundException("존재하지 않는 가게입니다.")
        );

        // 예약 정보 저장
        reservationRepository.save(
                Reservation.builder()
                        .reservationDate(dto.getDate())
                        .reservationTime(dto.getTime())
                        .visited(false)
                        .store(store)
                        .member(member)
                        .build()
        );

        // DTO 객체로 return
        return ReservationSuccessResponse.builder()
                .storeName(store.getName())
                .date(dto.getDate())
                .time(dto.getTime())
                .build();
    }

    /**
     * 예약확인하는 메서드. 유효성검사 진행
     */
    public ReservationVisitedResponse arrivalConfirmation(ArrivalConfirmationDto dto) {
        // 가게 PK 값과 유저의 ID 로 예약내역 불러오기
        List<Reservation> reservation = reservationRepository.findByStoreCodeAndMemberId(dto.getStoreCode(), dto.getMemberId(),dto.getReservationDate());
        // 예약을 확정지을 변수 선언
        Reservation nowReservation = new Reservation();

        // 예약 리스트가 사이즈가 0이면 예외 발생
        if (reservation.size() == 0) {
            throw new ReservationNotFoundException("예약 내역이 존재하지 않습니다.");
        }

        // 리스트를 순회하며 업데이트할 boolean 변수
        boolean reservationTimeInvalidException = true;
        // 고객 10분전 도착
        for (Reservation r : reservation) {
            // Entity의 예약 시간과 고객의 도착시간 비교.
            if (r.getReservationTime().getHour() - 1 == dto.getArrivalTime().getHour()
                    && dto.getArrivalTime().getMinute() + 10 <= 60) {
                // 변수 초기화
                nowReservation = r;
                // boolean 변수 설정
                reservationTimeInvalidException = false;
                break;
            }
        }

        // 방문 처리 가능한 예약이 없을때 예외 발생
        if (reservationTimeInvalidException) {
            throw new ReservationTimeInvalidException("유효한 예약이 없습니다.");
        }

        // 방문처리 해주고 다시 DB에 저장
        nowReservation.setVisited(true);
        reservationRepository.save(nowReservation);

        // DTO 로 response
        return ReservationVisitedResponse.builder()
                .storeName(nowReservation.getStore().getName())
                .memberId(nowReservation.getMember().getMemberId())
                .reservationDate(nowReservation.getReservationDate())
                .reservationTime(nowReservation.getReservationTime())
                .build();
    }

    /**
     * 전달받은 날짜에 해당하는 예약 목록 보여주는 메서드
     */
    public List<ReservationDetailResponse> getTodayReservation(TodayReservationListRequest dto,String memberId) {
        // storeRepository 에서 가게정보를 찾아온다.
        // 존재하지 않는다면 예외 발생
        Store store = storeRepository.findById(dto.getStoreCode()).orElseThrow(
                () -> new StoreNotFoundException("가게정보가 존재하지 않습니다.")
        );

        // 전달받은 JWT 의 아이디와 가게 사장님의 아이디가 다르면 예외발생
        if (!store.getMember().getMemberId().equals(memberId)) {
            throw new StoreMemberNotMatchException("본인 가게의 예약정보만 확인 가능합니다.");
        }

        // 해당 가게의 당일 예약목록
        List<Reservation> todayReservationList = reservationRepository.findAllReservationByStoreAndToday(dto.getStoreCode(), dto.getDate());
        // DTO 로 변환한 List 를 반호나
        return todayReservationList.stream().map(
                (data) -> ReservationDetailResponse.toDto(data)).collect(Collectors.toList());
    }
}
