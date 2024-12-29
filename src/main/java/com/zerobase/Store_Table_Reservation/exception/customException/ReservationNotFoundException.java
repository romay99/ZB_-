package com.zerobase.Store_Table_Reservation.exception.customException;

public class ReservationNotFoundException extends RuntimeException {
    public ReservationNotFoundException(String message) {
        super(message);
    }
}
