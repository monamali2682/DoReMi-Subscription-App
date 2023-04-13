package com.geektrust.backend.Repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.geektrust.backend.entities.Plan;


public class PlanRepository implements IPlanRepository{
    HashMap<String, Plan> map;
    private Integer autoIncrement;

    
    public PlanRepository() {
        this.map = new HashMap<String, Plan>();
        this.autoIncrement=0;
    }

    public PlanRepository(HashMap<String, Plan> map) {
        this.map = map;
        this.autoIncrement=map.size();
    }

    @Override
    public Plan save(Plan entity) {
        if( entity.fetchId() == null ){
            autoIncrement++;
            Plan plan = new Plan(Integer.toString(autoIncrement),entity.fetchCategory(),entity.fetchPlanName(),entity.fetchCost(),entity.fetchDuration(),entity.fetchNumberOfDevices());
            map.put(plan.fetchId(),plan);
            return plan;
        }
        map.put(entity.fetchId(),entity);
        return entity;
    }

    @Override
    public List<Plan> findAll() {
        return map.values().stream().collect(Collectors.toList());
    }

    @Override
    public Optional<Plan> findById(String id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public boolean existsById(String id) {
        return map.containsKey(id);
    }

    @Override
    public void delete(Plan entity) {
        map.remove(entity.fetchId());
    }

    @Override
    public void deleteById(String id) {
        map.remove(id); 
    }

    @Override
    public long count() {
       return map.size();
    }

    @Override
    public Optional<Plan> findByCategoryAndPlan(String category, String planName) {
        return map.values().stream().filter(p -> p.fetchPlanName().equals( planName)).filter(s -> s.fetchCategory().equals(category)).findFirst();
    }

}
