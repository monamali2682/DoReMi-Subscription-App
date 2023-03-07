package com.geektrust.backend.services;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import com.geektrust.backend.entities.Plan;
import com.geektrust.backend.entities.Subscription;
import com.geektrust.backend.Repositories.ISubscriptionRepository;

public class SubscriptionService implements ISubscriptionService{

    ISubscriptionRepository subscriptionRepository;
    IPlanService planService;
    
    public SubscriptionService(ISubscriptionRepository subscriptionRepository,IPlanService planService) {
        this.subscriptionRepository = subscriptionRepository;
        this.planService=planService;
    }

    @Override
    public String getRenewalInfo(Subscription subscription) {
        String renewalInfo="";
        LocalDate startDate = subscription.getStartDate();
        int validityInMonths= subscription.getPlanValidity();
        String reminderDate = getReminderDate(startDate,validityInMonths);
        renewalInfo+= "RENEWAL_REMINDER " + subscription.getCategory() + " " + reminderDate;
        return renewalInfo;
    }

    @Override
    public String getReminderDate(LocalDate startDate, int validityInMonths) {
        LocalDate subscritionEndDate = startDate.plusMonths(validityInMonths);
        LocalDate reminderDate = subscritionEndDate.minusDays(10);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-YYYY");
        return reminderDate.format(formatter);
    }

    @Override
    public Subscription createSubscription(String subscriptionCategory, String planName){
        Plan plan = planService.getPlan(subscriptionCategory, planName);
        Subscription subscription = new Subscription(subscriptionCategory,plan);
        Subscription savedSubscription = subscriptionRepository.save(subscription);
        return savedSubscription;
    }

    public Subscription modifyStartDate(Subscription subscription, LocalDate startDate) {
        subscription.modifyStartDate(startDate);
        Subscription savedSubscription = subscriptionRepository.save(subscription);
        return savedSubscription;
    }
}
