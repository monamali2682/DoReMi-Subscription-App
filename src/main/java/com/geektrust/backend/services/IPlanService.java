package com.geektrust.backend.services;

import com.geektrust.backend.entities.Plan;

public interface IPlanService {
    public Plan getPlan(String category, String planName);
    public Plan addPlan(String category, String planName, int cost, int duration);
}
