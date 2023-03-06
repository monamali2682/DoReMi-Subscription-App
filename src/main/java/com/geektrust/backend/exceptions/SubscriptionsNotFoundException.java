package com.geektrust.backend.exceptions;

public class SubscriptionsNotFoundException extends RuntimeException {
    public SubscriptionsNotFoundException(){
     super();
    }
    public SubscriptionsNotFoundException(String msg){
     super(msg);
    }
}
