package com.bank.accounts.exception;

import com.bank.accounts.dto.ResponseErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseErrorDto> handleGlobalException(Exception ex, WebRequest request) {
        ResponseErrorDto responseErrorDto = new ResponseErrorDto(
                request.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseErrorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(CustomerAlreadyExistsException.class)
    public ResponseEntity<ResponseErrorDto> handleCustomerAlreadyExistsException(CustomerAlreadyExistsException ex,
                                                                                 WebRequest request) {

        ResponseErrorDto responseErrorDto = new ResponseErrorDto(
                                                        request.getDescription(false),
                                                        HttpStatus.CONFLICT,
                                                        ex.getMessage(),
                                                        LocalDateTime.now());

        return new ResponseEntity<>(responseErrorDto, HttpStatus.CONFLICT);

    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseErrorDto> handleResourceNotFoundException(ResourceNotFoundException ex,
                                                                                 WebRequest request) {

        ResponseErrorDto responseErrorDto = new ResponseErrorDto(
                request.getDescription(false),
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                LocalDateTime.now());

        return new ResponseEntity<>(responseErrorDto, HttpStatus.NOT_FOUND);

    }

}
