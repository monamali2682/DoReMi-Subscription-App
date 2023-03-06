package com.geektrust.backend.Repositories;


import com.geektrust.backend.entities.Subscription;


public interface ISubscriptionRepository extends CRUDRepository<Subscription,String> {

    //public Optional<Subscription> findByCategoryAndPlan(String category, String planName);
}
