package com.geektrust.backend.services;

import com.geektrust.backend.entities.Plan;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
import com.geektrust.backend.Repositories.IPlanRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("PlanServiceTest")
@ExtendWith(MockitoExtension.class)
public class PlanServiceTest {
    
    @Mock
    private IPlanRepository planRepositoryMock;

    @InjectMocks
    private PlanService planService;

    @Test
    @DisplayName("Add Plan Method Should Create Plan")
    public void addPlan_ShouldReturnPlan(){
        //Arrange
        Plan expectedPlan = new Plan("1", "MUSIC", "FREE", 0, 1, 1);
        when(planRepositoryMock.save(any(Plan.class))).thenReturn(expectedPlan);

        //Act
        Plan actualPlan = planService.addPlan("MUSIC", "FREE", 0, 1);

        //Assert
        Assertions.assertEquals(expectedPlan,actualPlan);
        verify(planRepositoryMock,times(1)).save(any(Plan.class));
    }

    @Test
    @DisplayName("Get Plan Method Should Return Plan Given Category And Plan Name")
    public void getPlan_ShouldReturnPlanGivenCategoryAndPlanName(){
        //Arrange
        String category = "MUSIC";
        String planName = "FREE";
        Plan expectedPlan = new Plan("1", "MUSIC", "FREE", 0, 1, 1);
        when(planRepositoryMock.findByCategoryAndPlan(anyString(),anyString())).thenReturn(Optional.of(expectedPlan));

        //Act
        Plan actualPlan = planService.getPlan(category, planName);

        //Assert
        Assertions.assertEquals(expectedPlan, actualPlan);
        verify(planRepositoryMock,times(1)).findByCategoryAndPlan(anyString(), anyString());
    }

    
}
