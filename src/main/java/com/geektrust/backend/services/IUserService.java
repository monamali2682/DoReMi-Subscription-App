package com.geektrust.backend.services;

import com.geektrust.backend.dtos.PrintRenewalDTO;

public interface IUserService {
    public boolean validateDate(String date);
    public boolean startSubscription(String date);
    //public boolean addSubscription(String subscriptionCategory, String planName);
    //public boolean addTopUP(String topupName, int months);
    public PrintRenewalDTO printRenewalDates();
}
