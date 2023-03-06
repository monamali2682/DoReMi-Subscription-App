package com.geektrust.backend.commands;

import java.util.List;
import com.geektrust.backend.services.IUserService;

public class AddSubscriptionCommand implements ICommand {
    IUserService userService;
    

    public AddSubscriptionCommand(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(List<String> tokens) {
        String subscriptionCategory = tokens.get(1);
        String planName = tokens.get(2);
        try {
            boolean response =userService.addSubscription(subscriptionCategory, planName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
}
