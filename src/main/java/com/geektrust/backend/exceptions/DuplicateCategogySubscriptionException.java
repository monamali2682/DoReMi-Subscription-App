package com.geektrust.backend.exceptions;

public class DuplicateCategogySubscriptionException extends RuntimeException {
    public DuplicateCategogySubscriptionException(){
     super();
    }
    public DuplicateCategogySubscriptionException(String msg){
     super(msg);
    }
}
