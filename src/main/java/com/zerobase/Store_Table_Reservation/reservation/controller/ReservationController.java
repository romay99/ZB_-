package com.zerobase.Store_Table_Reservation.reservation.controller;

import com.zerobase.Store_Table_Reservation.reservation.dto.request.ReservationValidDto;
import com.zerobase.Store_Table_Reservation.reservation.dto.request.TodayReservationListRequest;
import com.zerobase.Store_Table_Reservation.reservation.dto.response.ReservationDetailResponse;
import com.zerobase.Store_Table_Reservation.reservation.dto.response.ReservationSuccessResponse;
import com.zerobase.Store_Table_Reservation.reservation.dto.response.ReservationVisitedResponse;
import com.zerobase.Store_Table_Reservation.reservation.service.ReservationService;
import com.zerobase.Store_Table_Reservation.store.dto.request.ArrivalConfirmationDto;
import com.zerobase.Store_Table_Reservation.store.dto.request.StoreReserveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationService reservationService;

    /**
     * 가게 예약하는 메서드. 로그인 필수
     */
    @PostMapping("/reserve")
    // 로그인 필수
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_PARTNER')")
    public ResponseEntity<ReservationSuccessResponse> reserveStore(@RequestBody StoreReserveDto dto) {
        // SecurityContextHolder 에서 인증된 사용자의 ID 를 가져온다.
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        ReservationSuccessResponse reservationSuccessResponse = reservationService.reserveStore(dto, username);
        return ResponseEntity.ok().body(reservationSuccessResponse);
    }

    /**
     * 도착확인 하는 메서드. 고객은 현장에 와서 키오스크로 확인하기 때문에 JWT 사용 불가
     * 현장에서 입력한 memberID 로 예약리스트를 찾는다.
     */
    @PostMapping("/visit")
    public ResponseEntity<ReservationVisitedResponse> arrivalConfirmation(@RequestBody ArrivalConfirmationDto dto) {
        ReservationVisitedResponse response = reservationService.arrivalConfirmation(dto);
        return ResponseEntity.ok().body(response);
    }

    /**
     * 가게의 특정 날짜 예약 목록 조회하는 메서드
     */
    @GetMapping("/list")
    public ResponseEntity<List<ReservationDetailResponse>> getTodayReservation(@RequestBody TodayReservationListRequest dto) {
        List<ReservationDetailResponse> todayReservation = reservationService.getTodayReservation(dto);
        return ResponseEntity.ok().body(todayReservation);
    }

    /**
     * 예약에 대한 수락 / 거절 메서드. (사장님 전용 메서드)
     */
    @PostMapping("/valid")
    @PreAuthorize("hasRole('ROLE_PARTNER')")
    public ResponseEntity<String> makeValidReservation(@RequestBody ReservationValidDto dto) {
        // SecurityContextHolder 에서 인증된 사용자의 ID 를 가져온다.
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean result = reservationService.makeValidReservation(dto, username);

        if (result) {
            return ResponseEntity.ok().body("예약이 확정되었습니다.");
        }
        return ResponseEntity.ok().body("예약이 거절되었습니다.");
    }
}
