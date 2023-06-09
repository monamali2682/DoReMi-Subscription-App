package com.geektrust.backend.entities;

public class Plan extends BaseEntity{
    static final int defaultNumberOfDevices=1;
    private String category;
    private String planName;
    private int cost;        /* cost in Rupees */
    private int duration;    /* duration in months*/
    private int numberOfDevices;

    public Plan(String category, String planName, int cost, int duration) {
        this.category=category;
        this.planName = planName;
        this.cost = cost;
        this.duration = duration;
        this.numberOfDevices = defaultNumberOfDevices;
    }

    public Plan(String id,String category,String planName, int cost, int duration, int numberOfDevices) {
        this(category, planName, cost, duration);
        this.id=id;
    }

    public String fetchPlanName() {
        return planName;
    }

    public int fetchCost() {
        return cost;
    }

    public int fetchDuration() {
        return duration;
    }

    public int fetchNumberOfDevices() {
        return numberOfDevices;
    }

    public void modifyNumberOfDevices(int devices){
        this.numberOfDevices=devices;
    }

    public String fetchCategory() {
        return category;
    }
    
}
