package com.geektrust.backend.entities;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("UserTest")
public class UserTest {

    @Test
    @DisplayName("isSubscriptionCategoryPresent Should Return True if Category Is Present")
    public void isSubscriptionCategoryPresent_ShouldReturnTrueIfCategoryPresent(){
        //Arrange
        String id="1";
        String name= "user";
        String startDateOfSubscription = "10-03-2022";
        String category ="PODCAST";
        Plan plan = new Plan(category, "FREE", 0, 1);
        List<Subscription> subscriptions= new ArrayList<>(Arrays.asList(new Subscription(category, plan)));
        TopUp topUp=null;
        int topupValidityInMonths=0;
        User user = new User(id, name, startDateOfSubscription, subscriptions, topUp, topupValidityInMonths);
        

        //Act
        boolean response= user.isSubscriptionCategoryPresent(category);

        //Act and Assert
        Assertions.assertTrue(response);
    }


    @Test
    @DisplayName("isSubscriptionCategoryPresent Should Return False if Category Is Not Present")
    public void isSubscriptionCategoryPresent_ShouldReturnFalseIfCategoryNotPresent(){
        //Arrange
        String id="1";
        String name= "user";
        String startDateOfSubscription = "10-03-2022";
        String category ="PODCAST";
        Plan plan = new Plan(category, "FREE", 0, 1);
        List<Subscription> subscriptions= new ArrayList<>(Arrays.asList(new Subscription("VIDEO", plan)));
        TopUp topUp=null;
        int topupValidityInMonths=0;
        User user = new User(id, name, startDateOfSubscription, subscriptions, topUp, topupValidityInMonths);
        
        //Act
        boolean response= user.isSubscriptionCategoryPresent(category);

        //Act and Assert
        Assertions.assertFalse(response);
    }

    @Test
    @DisplayName("addSubscription Should Add The Subscription")
    public void addSubscription_ShouldAddTheSubscription(){
        //Arrange
        String id="1";
        String name= "user";
        String startDateOfSubscription = "10-03-2022";
        List<Subscription> subscriptions= new ArrayList<>();
        TopUp topUp=null;
        int topupValidityInMonths=0;
        User user = new User(id, name, startDateOfSubscription, subscriptions, topUp, topupValidityInMonths);
        Plan plan = new Plan("1","VIDEO", "FREE", 0, 1,1);
        Subscription subscription= new Subscription("VIDEO", plan);
        
        //Act
        user.addSubscription(subscription);

        //Act and Assert
        Assertions.assertEquals(subscription,user.getSubscriptions().get(0));
    }


    @Test
    @DisplayName("getTopupAmount Should Return Topup Amount")
    public void gettopupAmount_ShouldReturnTopupAmount(){
        //Arrange
        String id="1";
        String name= "user";
        String startDateOfSubscription = "10-03-2022";
        List<Subscription> subscriptions= new ArrayList<>();
        TopUp topUp= new TopUp("1","FOUR_DEVICE",4,50);
        int topupValidityInMonths=0;
        User user = new User(id, name, startDateOfSubscription, subscriptions, topUp, topupValidityInMonths);
        
        //Act
        int topupAmount=user.getTopUp().getPerMonthCostInRupees();

        //Act and Assert
        Assertions.assertEquals(50,topupAmount);
    }
    

}
