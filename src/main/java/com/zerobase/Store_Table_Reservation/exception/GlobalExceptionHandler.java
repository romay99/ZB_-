package com.zerobase.Store_Table_Reservation.exception;

import com.zerobase.Store_Table_Reservation.exception.customException.IdAlreadyUsedException;
import com.zerobase.Store_Table_Reservation.exception.customException.IdNotNullException;
import com.zerobase.Store_Table_Reservation.exception.customException.MemberNotFoundException;
import com.zerobase.Store_Table_Reservation.exception.customException.MemberPasswordNotMatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 이미 사용중인 ID 에 대한 예외 처리
     */
    @ExceptionHandler(IdAlreadyUsedException.class)
    public ResponseEntity<ErrorResponse> handleIdAlreadyUsedException(IdAlreadyUsedException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * 회원가입 중 ID 값이 null 일때 예외 처리
     */
    @ExceptionHandler(IdNotNullException.class)
    public ResponseEntity<ErrorResponse> handleIdNotNullException(IdNotNullException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * 회원정보를 찾지 못했을때 예외 처리
     */
    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMemberNotFoundException(MemberNotFoundException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * 회원의 ID 와 비밀번호가 일치하지 않을때 예외 처리
     */
    @ExceptionHandler(MemberPasswordNotMatchException.class)
    public ResponseEntity<ErrorResponse> handleMemberPasswordNotMatchException(MemberPasswordNotMatchException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


}
