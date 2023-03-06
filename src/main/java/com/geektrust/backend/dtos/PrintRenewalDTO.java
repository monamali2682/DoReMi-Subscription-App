package com.geektrust.backend.dtos;

import java.util.ArrayList;
import java.util.List;

public class PrintRenewalDTO {
    /* renewalReminders = ["RENEWAL_REMINDER MUSIC 10-03-2022", "RENEWAL_REMINDERVIDEO 10-05-2022" ] */
    List<String> renewalReminders; 
    int renewalAmount;

    public PrintRenewalDTO() {
        this.renewalReminders= new ArrayList<>();
        this.renewalAmount=0;
    }

    public PrintRenewalDTO(List<String> renewalReminders, int renewalAmount) {
        this.renewalReminders = renewalReminders;
        this.renewalAmount = renewalAmount;
    }

    public List<String> getRenewalReminders() {
        return renewalReminders;
    }

    public int getRenewalAmount() {
        return renewalAmount;
    }

    public void addRenewalReminder(String renewalReminder){
        this.renewalReminders.add(renewalReminder);
    }

    public void addRenewalAmount(int amount){
        this.renewalAmount+=amount;
    }

    @Override
    public String toString() {
        String ans ="";
        for(String renewalreminder : renewalReminders){
            ans+=renewalreminder+"\n";
        }
        ans+="RENEWAL_AMOUNT " + renewalAmount;
        return ans;
    }

}
