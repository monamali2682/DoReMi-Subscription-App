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

    public String fetchCategory() {
        return category;
    }

    public LocalDate fetchStartDate() {
        return startDate;
    }

    public Plan fetchPlan() {
        return plan;
    }

    public int fetchSubscriptionAmount() {
        return this.plan.fetchCost();
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
