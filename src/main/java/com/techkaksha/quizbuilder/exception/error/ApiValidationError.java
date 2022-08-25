package com.techkaksha.quizbuilder.exception.error;

public class ApiValidationError extends ApiSubError{
    private String object;
    private String field;
    private String rejectedValue;
    private String message;

    ApiValidationError(String object, String message){
        this.object = object;
        this.message = message;
    }
}
