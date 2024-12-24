package com.zerobase.Store_Table_Reservation.exception.customException;

public class StoreNotFoundException extends RuntimeException{
    public StoreNotFoundException(String message) {
        super(message);
    }
}
