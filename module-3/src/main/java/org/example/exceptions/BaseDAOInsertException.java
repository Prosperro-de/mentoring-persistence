package org.example.exceptions;


public class BaseDAOInsertException extends RuntimeException {

    public BaseDAOInsertException(String exceptionMessage){
        super(exceptionMessage);
    }
}
