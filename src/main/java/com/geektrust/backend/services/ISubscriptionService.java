package com.geektrust.backend.services;

import java.time.LocalDate;
import com.geektrust.backend.entities.Subscription;

public interface ISubscriptionService {

    public String getRenewalInfo(Subscription subscription);
    public Subscription createSubscription(String subscriptionCategory, String planName);
    public Subscription modifyStartDate(Subscription subscription, LocalDate startDate);
    public String getReminderDate(LocalDate startDate, int validityInMonths);
    public boolean addSubscription(String subscriptionCategory, String planName);
    
}
