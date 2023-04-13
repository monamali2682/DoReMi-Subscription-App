package com.geektrust.backend.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("PlanTest")
public class PlanTest{
    
    @Test
    @DisplayName("modifyNumberOfDevices Should Modify Number Of Devices")
    public void modifyNumberOfDevices_ShouldModifyNumberOfDevices(){
        //Arrange
        String id="1";
        String category= "MUSIC";
        String planName ="PERSONAL";
        int cost=100;
        int duration=1;
        int numberOfDevices=1;
        Plan plan = new Plan(id, category, planName,  cost,  duration,  numberOfDevices);

        //Act
        plan.modifyNumberOfDevices(4);

        //Act and Assert
        Assertions.assertEquals(plan.fetchNumberOfDevices(),4);
    }
    

}
