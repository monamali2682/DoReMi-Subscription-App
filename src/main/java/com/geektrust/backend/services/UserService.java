package com.geektrust.backend.services;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import com.geektrust.backend.entities.User;
import com.geektrust.backend.exceptions.InvalidDateException;
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


    



}
