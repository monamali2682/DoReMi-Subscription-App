package com.geektrust.backend.commands;

import java.util.List;
import com.geektrust.backend.dtos.PrintRenewalDTO;
import com.geektrust.backend.services.IUserService;

public class PrintRenewalDatesCommand implements ICommand{
    private IUserService userService;
    
    public PrintRenewalDatesCommand(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(List<String> tokens) {
        try {
            PrintRenewalDTO dto = userService.printRenewalDates();
            System.out.println(dto);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
}
