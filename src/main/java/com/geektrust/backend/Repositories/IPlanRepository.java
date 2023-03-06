package com.geektrust.backend.Repositories;

import java.util.Optional;
import com.geektrust.backend.entities.Plan;

public interface IPlanRepository extends CRUDRepository <Plan,String>{
    public Optional<Plan> findByCategoryAndPlan(String category, String planName);
}
