package com.geektrust.backend.entities;

public class TopUp extends BaseEntity{

    private String topupName;
    private  int numberOfDevices;
    private  int perMonthCostInRupees;
    
    public TopUp( String topupName,int numberOfDevices, int costInRupees) {
        this.topupName= topupName;
        this.numberOfDevices = numberOfDevices;
        this.perMonthCostInRupees = costInRupees;
    }
    
    public TopUp(String id,  String topupName,int numberOfDevices,int costInRupees) {
        this(topupName, numberOfDevices, costInRupees);
        this.id=id;
    }

    public int getNumberOfDevices() {
        return numberOfDevices;
    }
    
    public String getTopupName() {
        return topupName;
    }

    public int getPerMonthCostInRupees() {
        return perMonthCostInRupees;
    }
    
}
