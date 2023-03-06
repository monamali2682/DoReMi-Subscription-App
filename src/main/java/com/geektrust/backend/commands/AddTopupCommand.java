package com.geektrust.backend.commands;

import java.util.List;
import com.geektrust.backend.services.IUserService;

public class AddTopupCommand implements ICommand{
    IUserService userService;
    

    public AddTopupCommand(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(List<String> tokens) {
        String topupName = tokens.get(1);
        int validityInMonths = Integer.parseInt(tokens.get(2));
        try {
            boolean response = userService.addTopUP(topupName, validityInMonths);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
}
