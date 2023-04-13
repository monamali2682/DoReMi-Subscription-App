package com.geektrust.backend.services;

import com.geektrust.backend.entities.TopUp;

public interface ITopupService {
    public TopUp getTopUp(String topupName);
    public TopUp addTopup(String topupName, int numberOfDevices ,int costInRupees);
    public boolean addTopUP(String topupName, int validityInMonths);
}