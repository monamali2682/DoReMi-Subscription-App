package com.geektrust.backend.entities;

import java.util.ArrayList;
import java.util.List;

public class User extends BaseEntity {
    private final String name;
    private String startDateOfSubscription;
    private List<Subscription> subscriptions;
    private TopUp topUp;
    private int topupValidityInMonths;

    public User() {
        this.id=null;
        this.name="user";
        this.startDateOfSubscription=null;
        this.subscriptions=new ArrayList<>();
        this.topUp=null;
        this.topupValidityInMonths=0;
    }

    public User(String id,String name, String startDateOfSubscription,List<Subscription> subscriptions, TopUp topUp, int topupValidityInMonths) {
        this(name,startDateOfSubscription,subscriptions,topUp,topupValidityInMonths);
        this.id=id;
    }
    

    public User(String name, String startDateOfSubscription, List<Subscription> subscriptions,
            TopUp topUp, int topupValidityInMonths) {
        this.name = name;
        this.startDateOfSubscription = startDateOfSubscription;
        this.subscriptions = subscriptions;
        this.topUp = topUp;
        this.topupValidityInMonths = topupValidityInMonths;
    }

    public String getName() {
        return name;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public TopUp getTopUp() {
        return topUp;
    }

    public void modifyTopUp(TopUp topUp) {
        this.topUp = topUp;
    }

    public String getStartDateOfSubscription() {
        return startDateOfSubscription;
    }

    public int getTopupValidityInMonths() {
        return topupValidityInMonths;
    }

    public void modifyTopupValidityInMonths(int topupValidityInMonths) {
        this.topupValidityInMonths = topupValidityInMonths;
    }

    public void modifyStartDateOfSubscription(String startDateOfSubscription) {
        this.startDateOfSubscription = startDateOfSubscription;
    }

    @Override
    public String toString() {
        return "User [name=" + name + "]";
    }
    
    // test below methods 
    public void addSubscription(Subscription subscription){
        this.subscriptions.add(subscription);
    }

    public boolean isSubscriptionCategoryPresent(String category) {
        for(Subscription subscription:subscriptions){
            if(subscription.getCategory().equals(category)) return true;
        }
        return false;
    }
    
    public int gettopupAmount(){
        return this.topUp.getPerMonthCostInRupees();
    }

    
    
    
    


}
