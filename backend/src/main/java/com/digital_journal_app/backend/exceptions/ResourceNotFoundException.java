package com.digital_journal_app.backend.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    private String resourceName;
    private String field;
    private Long fieldId;
    private String fieldName;

    public ResourceNotFoundException(String resourceName ,String field , Long fieldId){
        super(String.format("%s not found with %s : %d",resourceName,field,fieldId));
        this.resourceName=resourceName;
        this.field=field;
        this.fieldId=fieldId;
    }
    public ResourceNotFoundException(String resourceName ,String field , String fieldName){
        super(String.format("%s not found with %s : %s",resourceName,field,fieldName));
        this.resourceName=resourceName;
        this.field=field;
        this.fieldName=fieldName;
    }

}
