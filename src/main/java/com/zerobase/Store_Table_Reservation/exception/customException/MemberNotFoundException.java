package com.zerobase.Store_Table_Reservation.exception.customException;

public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException(String message) {
        super(message);
    }
}
