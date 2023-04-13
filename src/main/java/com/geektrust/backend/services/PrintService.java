package com.geektrust.backend.services;

import com.geektrust.backend.Repositories.IUserRepository;
import com.geektrust.backend.dtos.PrintRenewalDTO;
import com.geektrust.backend.entities.Subscription;
import com.geektrust.backend.entities.User;
import com.geektrust.backend.exceptions.SubscriptionsNotFoundException;

public class PrintService implements IPrintService{
    IUserRepository userRepository;
    ISubscriptionService subscriptionService;

    public PrintService(IUserRepository userRepository, ISubscriptionService subscriptionService) {
        this.userRepository = userRepository;
        this.subscriptionService=subscriptionService;
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
