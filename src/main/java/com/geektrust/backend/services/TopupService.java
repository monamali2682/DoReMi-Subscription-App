package com.geektrust.backend.services;

import com.geektrust.backend.entities.TopUp;
import com.geektrust.backend.entities.User;
import com.geektrust.backend.exceptions.DuplicateTopupFoundException;
import com.geektrust.backend.exceptions.InvalidDateException;
import com.geektrust.backend.exceptions.SubscriptionsNotFoundException;
import com.geektrust.backend.Repositories.ITopupRepository;
import com.geektrust.backend.Repositories.IUserRepository;

public class TopupService implements ITopupService {
    ITopupRepository topupRepository;
    IUserRepository userRepository;

    public TopupService(ITopupRepository topupRepository, IUserRepository userRepository) {
        this.topupRepository = topupRepository;
        this.userRepository=userRepository;
    }
    
    @Override
    public TopUp getTopUp(String topupName){
        TopUp topup = topupRepository.findByname(topupName).orElseThrow(()-> new RuntimeException("Topup not found"));
        return topup;
    }
    
    @Override
    public TopUp addTopup(String topupName, int numberOfDevices ,int costInRupees){
        TopUp topup = new TopUp(topupName, numberOfDevices,costInRupees);
        return topupRepository.save(topup);
    }

    @Override
    public boolean addTopUP(String topupName, int validityInMonths){
        /*Optional requirement :- User name can also be provided 
         * Here for simplicity, we will use the name "user" , that we provided while creating the user instance in START_SUBSCRIPTION command
        */
        User user= userRepository.getUser("user");
        if(user.fetchTopUp()!=null){
            throw new DuplicateTopupFoundException("ADD_TOPUP_FAILED DUPLICATE_TOPUP");
        }
        if(user.fetchStartDateOfSubscription()==null){
            throw new InvalidDateException("ADD_TOPUP_FAILED INVALID_DATE");
        }
        if(user.fetchSubscriptions().isEmpty()){
            throw new SubscriptionsNotFoundException("ADD_TOPUP_FAILED SUBSCRIPTIONS_NOT_FOUND");
        }
        TopUp topup = this.getTopUp(topupName);
        user.modifyTopUp(topup);
        user.modifyTopupValidityInMonths(validityInMonths);
        userRepository.save(user);
        return true;
    }
}
