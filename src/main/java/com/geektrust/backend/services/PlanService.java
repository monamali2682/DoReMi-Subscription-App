package com.geektrust.backend.services;

import com.geektrust.backend.Repositories.IPlanRepository;
import com.geektrust.backend.entities.Plan;

public class PlanService implements IPlanService {
    IPlanRepository planRepository;

    public PlanService(IPlanRepository planRepository) {
        this.planRepository = planRepository;
    }
    
    @Override
    public Plan getPlan(String category, String planName){
        Plan plan = planRepository.findByCategoryAndPlan(category, planName).orElseThrow(()-> new RuntimeException("Plan not found"));
        return plan;
    }
    
    @Override
    public Plan addPlan(String category, String planName, int cost, int duration){
        Plan plan = new Plan(category, planName,cost,duration);
        return planRepository.save(plan);
    }
}
