package com.geektrust.backend.Repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.geektrust.backend.entities.TopUp;


public class TopupRepository implements ITopupRepository{
    HashMap<String, TopUp> map;
    private Integer autoIncrement;

    
    public TopupRepository() {
        this.map = new HashMap<String, TopUp>();
        this.autoIncrement = 0;
    }


    public TopupRepository(HashMap<String, TopUp> map) {
        this.map = map;
        this.autoIncrement = map.size();
    }


    @Override
    public TopUp save(TopUp entity) {
        if( entity.getId() == null ){
            autoIncrement++;
            TopUp topup = new TopUp(Integer.toString(autoIncrement),entity.getTopupName(),entity.getNumberOfDevices(),entity.getPerMonthCostInRupees());
            map.put(topup.getId(),topup);
            return topup;
        }
        map.put(entity.getId(),entity);
        return entity;
    }


    @Override
    public List<TopUp> findAll() {
        return map.values().stream().collect(Collectors.toList());
    }


    @Override
    public Optional<TopUp> findById(String id) {
        return Optional.ofNullable(map.get(id));
    }


    @Override
    public boolean existsById(String id) {
        return map.containsKey(id);
    }


    @Override
    public void delete(TopUp entity) {
        map.remove(entity.getId());
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
    public Optional<TopUp> findByname(String topupName) {
        return map.values().stream().filter(p -> p.getTopupName().equals(topupName)).findFirst();
    }
}
