package com.geektrust.backend.services;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import com.geektrust.backend.dtos.PrintRenewalDTO;
import com.geektrust.backend.entities.Subscription;
import com.geektrust.backend.entities.TopUp;
import com.geektrust.backend.entities.User;
import com.geektrust.backend.exceptions.DuplicateCategogySubscriptionException;
import com.geektrust.backend.exceptions.DuplicateTopupFoundException;
import com.geektrust.backend.exceptions.InvalidDateException;
import com.geektrust.backend.exceptions.SubscriptionsNotFoundException;
import com.geektrust.backend.Repositories.IUserRepository;

public class UserService implements IUserService{
    IUserRepository userRepository;
    ISubscriptionService subscriptionService;
    ITopupService topupService;
    

    public UserService(IUserRepository userRepository,ISubscriptionService subscriptionService, ITopupService topupService){
        this.userRepository = userRepository;
        this.subscriptionService = subscriptionService;
        this.topupService=topupService;
    }

    @Override
    public boolean validateDate(String date){
        if (date.trim().equals("")){
            return true;
        }
        else{
            //Set date format as dd-MM-yyyy
            SimpleDateFormat sdformat = new SimpleDateFormat("dd-MM-yyyy");
            sdformat.setLenient(false);
            try{
                sdformat.parse(date); 
            }
            catch (ParseException e){
                return false;
            }
            return true;
        }
    }


    @Override
    public boolean startSubscription(String date){
        User user= userRepository.getUser("user");
        
        if(!validateDate(date)){
            throw new InvalidDateException("INVALID_DATE");
        }
        user.setStartDateOfSubscription(date);
        userRepository.save(user);
        return true;
    }


   @ Override
    public boolean addSubscription(String subscriptionCategory, String planName){
        /*Optional requirement :- User name can also be provided 
         * Here for simplicity, we will use the name "user" , that we provided while creating the user instance in START_SUBSCRIPTION command
        */
        User user= userRepository.getUser("user");
        if(user.getStartDateOfSubscription()==null){
            throw new InvalidDateException("ADD_SUBSCRIPTION_FAILED INVALID_DATE");
        }
        if(user.isSubscriptionCategoryPresent(subscriptionCategory)){
            throw new DuplicateCategogySubscriptionException("ADD_SUBSCRIPTION_FAILED DUPLICATE_CATEGORY");
        }

        Subscription subscription = subscriptionService.createSubscription(subscriptionCategory, planName);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate startDate = LocalDate.parse(user.getStartDateOfSubscription(),formatter);
        Subscription subscriptionModified= subscriptionService.modifyStartDate(subscription, startDate);
        user.addSubscription(subscriptionModified);
        userRepository.save(user);
        return true;
    }



    @Override
    public boolean addTopUP(String topupName, int validityInMonths){
        /*Optional requirement :- User name can also be provided 
         * Here for simplicity, we will use the name "user" , that we provided while creating the user instance in START_SUBSCRIPTION command
        */
        User user= userRepository.getUser("user");
        if(user.getTopUp()!=null){
            throw new DuplicateTopupFoundException("ADD_TOPUP_FAILED DUPLICATE_TOPUP");
        }
        if(user.getSubscriptions().isEmpty()){
            throw new SubscriptionsNotFoundException("ADD_TOPUP_FAILED SUBSCRIPTIONS_NOT_FOUND");
        }
        TopUp topup = topupService.getTopUp(topupName);
        user.setTopUp(topup);
        user.setTopupValidityInMonths(validityInMonths);
        userRepository.save(user);
        return true;
    }

    @Override
    public PrintRenewalDTO printRenewalDates(){
        User user= userRepository.getUser("user");
        if(user.getSubscriptions().isEmpty()){
            throw new SubscriptionsNotFoundException("SUBSCRIPTIONS_NOT_FOUND");
        }
        PrintRenewalDTO printRenewalDTO = new PrintRenewalDTO();
        for(Subscription subscription : user.getSubscriptions()){
            String renewalInfo= subscriptionService.getRenewalInfo(subscription);
            printRenewalDTO.addRenewalReminder(renewalInfo);
            printRenewalDTO.addRenewalAmount(subscription.getSubscriptionAmount());
        }
        if(user.getTopUp()!=null){
            int topupAmount = user.gettopupAmount()*user.getTopupValidityInMonths();
            printRenewalDTO.addRenewalAmount(topupAmount);
        }
        return printRenewalDTO;
    }



}
