package com.geektrust.backend.commands;

import java.util.List;
import com.geektrust.backend.services.IUserService;

public class StartSubcriptionCommand implements ICommand{
    IUserService userService;
    

    public StartSubcriptionCommand(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(List<String> tokens) {
        String date = tokens.get(1);
        try {
            boolean response = userService.startSubscription(date);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
}
