package com.geektrust.backend.repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import com.geektrust.backend.Repositories.PlanRepository;
import com.geektrust.backend.entities.Plan;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("PlanRepositoryTest")
public class PlanRepositoryTest{
    private PlanRepository planRepository;

    @BeforeEach
    void setup(){
        final HashMap<String,Plan> planMap = new HashMap<String,Plan>(){
            {
                put("1", new Plan("1", "MUSIC", "FREE", 0, 1, 1));
                put("2", new Plan("2", "MUSIC", "PERSONAL", 100, 1, 1));
                put("3", new Plan("3", "PODCAST", "PREMIUM", 300, 3, 1));
            }
        };
        planRepository = new PlanRepository(planMap);
    }

    @Test
    @DisplayName("save method should create and return new Plan")
    public void savePlanTest(){
        //Arrange
        Plan plan4 = new Plan( "PODCAST", "FREE", 0, 1);
        Plan expectedPlan = new Plan("4", "PODCAST", "FREE", 0, 1,1);
        //Act
        Plan actualPlan = planRepository.save(plan4);
        //Assert
        Assertions.assertEquals(expectedPlan,actualPlan);
    }


    @Test
    @DisplayName("findAll method should return All Plan")
    public void findAllPlanTest(){
        //Arrange
        int expectedCount = 3;
        //Act
        List<Plan> actualPlans = planRepository.findAll();
        //Assert
        Assertions.assertEquals(expectedCount,actualPlans.size());
    }

    @Test
    @DisplayName("findAll method should return Empty List if No Plans Found")
    public void findAllPlan_ShouldReturnEmptyList(){
        //Arrange
        int expectedCount = 0;
        PlanRepository emptyPlanRepository = new PlanRepository();
        //Act
        List<Plan> actualPlans = emptyPlanRepository.findAll();
        //Assert
        Assertions.assertEquals(expectedCount,actualPlans.size());
    }


    @Test
    @DisplayName("findById method should return Plan Given Id")
    public void findById_ShouldReturnPlan_GivenPlanId(){
        //Arrange
        String expectedPlanId = "3";
        //Act
        Optional<Plan> actualPlan = planRepository.findById(expectedPlanId);
        //Assert
        Assertions.assertEquals(expectedPlanId,actualPlan.get().fetchId());
    }

    @Test
    @DisplayName("findById method should return empty if Plan Not Found")
    public void findById_ShouldReturnEmptyIfPlanNotFound(){
        //Arrange
        Optional<Plan> expected = Optional.empty();
        //Act
        Optional<Plan> actual = planRepository.findById("5");
        //Assert
        Assertions.assertEquals(expected,actual);
    }


    @Test
    @DisplayName("findByCategoryAndPlan method should return Plan Given Category And Plan")
    public void findByCategoryAndPlan_ShouldReturnPlanGivenCategoryAndPlan(){
        //Arrange
        String category= "MUSIC";
        String planName = "PERSONAL";
        Plan expectedPlan = new Plan("2", "MUSIC", "PERSONAL", 100, 1, 1);

        //Act
        Plan actualPlan = planRepository.findByCategoryAndPlan(category, planName).get();
        //Assert
        Assertions.assertTrue(expectedPlan.equals(actualPlan));
    }

}
