package com.geektrust.backend.services;

import com.geektrust.backend.entities.TopUp;
import com.geektrust.backend.Repositories.ITopupRepository;

public class TopupService implements ITopupService {
    ITopupRepository topupRepository;

    public TopupService(ITopupRepository topupRepository) {
        this.topupRepository = topupRepository;
    }
    
    @Override
    public TopUp getTopUp(String topupName){
        TopUp topup = topupRepository.findByname(topupName).orElseThrow(()-> new RuntimeException("Topup not found"));
        return topup;
    }
    
    @Override
    public TopUp addTopup(String topupName, int numberOfDevices ,int costInRupees){
        TopUp topup = new TopUp(topupName, numberOfDevices,costInRupees);
        return topupRepository.save(topup);
    }
}
