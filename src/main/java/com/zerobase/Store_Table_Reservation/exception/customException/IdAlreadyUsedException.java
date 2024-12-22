package com.zerobase.Store_Table_Reservation.exception.customException;

public class IdAlreadyUsedException extends RuntimeException {
    public IdAlreadyUsedException(String message) {
        super(message);
    }
}
