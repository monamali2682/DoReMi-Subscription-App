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
        user.modifyStartDateOfSubscription(date);
        userRepository.save(user);
        return true;
    }


    @Override
    public PrintRenewalDTO printRenewalDates(){
        User user= userRepository.getUser("user");
        if(user.fetchSubscriptions().isEmpty()){
            throw new SubscriptionsNotFoundException("SUBSCRIPTIONS_NOT_FOUND");
        }
        PrintRenewalDTO printRenewalDTO = new PrintRenewalDTO();
        for(Subscription subscription : user.fetchSubscriptions()){
            String renewalInfo= subscriptionService.getRenewalInfo(subscription);
            printRenewalDTO.addRenewalReminder(renewalInfo);
            printRenewalDTO.addRenewalAmount(subscription.fetchSubscriptionAmount());
        }
        if(user.fetchTopUp()!=null){
            int topupAmount = user.fetchTopUp().fetchPerMonthCostInRupees()*user.fetchTopupValidityInMonths();
            printRenewalDTO.addRenewalAmount(topupAmount);
        }
        return printRenewalDTO;
    }



}
