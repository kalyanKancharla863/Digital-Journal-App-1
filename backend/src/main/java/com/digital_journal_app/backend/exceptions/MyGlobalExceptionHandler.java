package com.digital_journal_app.backend.exceptions;

import com.digital_journal_app.backend.payload.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyGlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIResponse> myResourceNotFoundExceptionHandler(ResourceNotFoundException e){
        APIResponse apiResponse = new APIResponse(e.getMessage(), false);
    return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(APIException.class)
    public ResponseEntity<APIResponse> myAPIException(APIException e){
        return new ResponseEntity<>( new APIResponse(e.getMessage(),false) ,HttpStatus.BAD_REQUEST);
    }
}
