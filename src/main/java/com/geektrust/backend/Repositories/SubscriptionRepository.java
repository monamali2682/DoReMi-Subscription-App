package com.geektrust.backend.Repositories;


import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.geektrust.backend.entities.Subscription;


public class SubscriptionRepository implements ISubscriptionRepository {
    HashMap<String, Subscription> map;
    private Integer autoIncrement;

    
    public SubscriptionRepository() {
        this.map = new HashMap<String, Subscription>();
        this.autoIncrement=0;
    }

    public SubscriptionRepository(HashMap<String, Subscription> map) {
        this.map = map;
        this.autoIncrement = map.size();
    }

    @Override
    public Subscription save(Subscription entity) {
        if( entity.fetchId() == null ){
            autoIncrement++;
            Subscription subscription = new Subscription(Integer.toString(autoIncrement),entity.fetchCategory(),entity.fetchStartDate(),entity.fetchPlan());
            map.put(subscription.fetchId(),subscription);
            return subscription;
        }
        map.put(entity.fetchId(),entity);
        return entity;
    }

    @Override
    public List<Subscription> findAll() {
        return map.values().stream().collect(Collectors.toList());
    }

    @Override
    public Optional<Subscription> findById(String id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public boolean existsById(String id) {
        return map.containsKey(id);
    }

    @Override
    public void delete(Subscription entity) {
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

    
}
