package com.geektrust.backend.commands;

import java.util.List;
import com.geektrust.backend.services.ISubscriptionService;

public class AddSubscriptionCommand implements ICommand {
    ISubscriptionService subscriptionService;

    public AddSubscriptionCommand(ISubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @Override
    public void execute(List<String> tokens) {
        String subscriptionCategory = tokens.get(1);
        String planName = tokens.get(2);
        try {
            boolean response =subscriptionService.addSubscription(subscriptionCategory, planName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
}
