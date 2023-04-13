package com.geektrust.backend.commands;

import java.util.List;
import com.geektrust.backend.dtos.PrintRenewalDTO;
import com.geektrust.backend.services.IPrintService;


public class PrintRenewalDatesCommand implements ICommand{
    private  IPrintService printService;
    
    public PrintRenewalDatesCommand(IPrintService printService) {
        this.printService = printService;
    }

    @Override
    public void execute(List<String> tokens) {
        try {
            PrintRenewalDTO dto = printService.printRenewalDates();
            System.out.println(dto);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
}
