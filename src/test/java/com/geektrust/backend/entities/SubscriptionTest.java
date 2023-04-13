package com.geektrust.backend.entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("SubscriptionTest")
public class SubscriptionTest{

    @Test
    @DisplayName("setStartDate Should change Start Date Of Subscription")
    public void setStartDate_ShouldChangeStartDateOfSubscription(){
        //Arrange
        String id="1";
        String category= "MUSIC";
        LocalDate startDate=null;
        Plan plan = new Plan(category, "FREE", 0, 1);
        Subscription subscription = new Subscription(id, category, startDate,plan);

        //Act
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate newStartDate = LocalDate.parse("10-03-2022", formatter);
        subscription.modifyStartDate(newStartDate);

        //Act and Assert
        Assertions.assertEquals(subscription.fetchStartDate(),newStartDate);
    }
   
}
