package com.geektrust.backend.exceptions;

public class DuplicateTopupFoundException extends RuntimeException {
    public DuplicateTopupFoundException(){
     super();
    }
    public DuplicateTopupFoundException(String msg){
     super(msg);
    }
}

