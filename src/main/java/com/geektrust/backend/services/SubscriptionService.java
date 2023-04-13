package com.geektrust.backend.services;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import com.geektrust.backend.entities.Plan;
import com.geektrust.backend.entities.Subscription;
import com.geektrust.backend.entities.User;
import com.geektrust.backend.exceptions.DuplicateCategogySubscriptionException;
import com.geektrust.backend.exceptions.InvalidDateException;
import com.geektrust.backend.Repositories.ISubscriptionRepository;
import com.geektrust.backend.Repositories.IUserRepository;

public class SubscriptionService implements ISubscriptionService{

    ISubscriptionRepository subscriptionRepository;
    IPlanService planService;
    IUserRepository userRepository;
    
    public SubscriptionService(ISubscriptionRepository subscriptionRepository,IPlanService planService,IUserRepository  userRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.planService=planService;
        this.userRepository=userRepository;
    }

    @Override
    public String getRenewalInfo(Subscription subscription) {
        String renewalInfo="";
        LocalDate startDate = subscription.fetchStartDate();
        int validityInMonths= subscription.fetchPlan().fetchDuration();
        String reminderDate = getReminderDate(startDate,validityInMonths);
        renewalInfo+= "RENEWAL_REMINDER " + subscription.fetchCategory() + " " + reminderDate;
        return renewalInfo;
    }

    @Override
    public String getReminderDate(LocalDate startDate, int validityInMonths) {
        LocalDate subscritionEndDate = startDate.plusMonths(validityInMonths);
        LocalDate reminderDate = subscritionEndDate.minusDays(10);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
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

    @ Override
    public boolean addSubscription(String subscriptionCategory, String planName){
        /*Optional requirement :- User name can also be provided 
         * Here for simplicity, we will use the name "user" , that we provided while creating the user instance in START_SUBSCRIPTION command
        */
        User user= userRepository.getUser("user");
        if(user.fetchStartDateOfSubscription()==null){
            throw new InvalidDateException("ADD_SUBSCRIPTION_FAILED INVALID_DATE");
        }
        if(user.isSubscriptionCategoryPresent(subscriptionCategory)){
            throw new DuplicateCategogySubscriptionException("ADD_SUBSCRIPTION_FAILED DUPLICATE_CATEGORY");
        }

        Subscription subscription = this.createSubscription(subscriptionCategory, planName);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate startDate = LocalDate.parse(user.fetchStartDateOfSubscription(),formatter);
        Subscription subscriptionModified= this.modifyStartDate(subscription, startDate);
        user.addSubscription(subscriptionModified);
        userRepository.save(user);
        return true;
    }
}
