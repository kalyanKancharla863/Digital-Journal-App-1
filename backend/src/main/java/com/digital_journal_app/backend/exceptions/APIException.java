package com.digital_journal_app.backend.exceptions;

public class APIException extends  RuntimeException {
    private String message;
    public APIException(String message){
        super(message);
    }
}
