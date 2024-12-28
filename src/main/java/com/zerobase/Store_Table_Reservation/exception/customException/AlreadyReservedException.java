package com.zerobase.Store_Table_Reservation.exception.customException;

public class AlreadyReservedException extends RuntimeException {
    public AlreadyReservedException(String message) {
        super(message);
    }
}
