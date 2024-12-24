package com.zerobase.Store_Table_Reservation.reservation.service;

import com.zerobase.Store_Table_Reservation.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
}
