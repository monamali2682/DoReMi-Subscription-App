package com.geektrust.backend.Repositories;

import java.util.Optional;
import com.geektrust.backend.entities.TopUp;


public interface ITopupRepository extends CRUDRepository<TopUp, String>{

    Optional<TopUp> findByname(String topupName);
    
}
