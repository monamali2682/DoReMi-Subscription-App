package com.geektrust.backend.entities;

import java.time.LocalDate;

public class Subscription extends BaseEntity{
    private String category;
    private LocalDate startDate;
    private Plan plan;

    public Subscription(String category,Plan plan) {
        this.category = category;
        this.startDate = null;
        this.plan = plan;
    }

    public Subscription(String category, LocalDate startDate, Plan plan) {
        this(category, plan);
        this.startDate = startDate;
    }

    public Subscription(String id,String category, LocalDate startDate, Plan plan) {
        this(category, startDate, plan);
        this.id=id;
    }

    public String getCategory() {
        return category;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Plan getPlan() {
        return plan;
    }

    public int getSubscriptionAmount() {
        return this.plan.getCost();
    }

    // public String getPlanName(){
    //     return this.plan.getPlanName();
    // }

    // public int getPlanValidity(){
    //     return this.plan.getDuration();
    // }

    @Override
    public String toString() {
        return "Subscription [category=" + category + ", plan=" + plan + "]";
    }

    public void modifyStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
   
}
