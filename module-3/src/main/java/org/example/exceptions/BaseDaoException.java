package org.example.exceptions;

public class BaseDaoException extends RuntimeException{
    public BaseDaoException(String exceptionMessage){
        super(exceptionMessage);
    }

    public BaseDaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
