package com.theh.moduleuser.Exceptions;

public class ApiException extends RuntimeException{
    public ApiException (String message){
        super(message);
    }
}
