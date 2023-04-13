package com.geektrust.backend.services;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import com.geektrust.backend.entities.Subscription;
import com.geektrust.backend.entities.TopUp;
import com.geektrust.backend.entities.User;

import com.geektrust.backend.exceptions.InvalidDateException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.geektrust.backend.Repositories.IUserRepository;

@DisplayName("UserServiceTest")
@ExtendWith(MockitoExtension.class)
public class UserServiceTest{

    @Mock
    IUserRepository userRepositoryMock;
    @Mock
    ISubscriptionService subscriptionServiceMock;
    @Mock
    ITopupService topupServiceMock;
    
    @InjectMocks
    UserService userService;


    @Test
    @DisplayName("Validate Date Method Should Return True For Valid Date")
    public void validateDate_shouldReturnTrue_GivenValidDate(){
        //Arrange
        String date= "02-02-2020";

        //Act &
        //Assert
        Assertions.assertTrue(userService.validateDate(date));
    }

    @Test
    @DisplayName("Validate Date Method Should Return false For Invalid Date")
    public void validateDate_shouldReturnFalse_GivenInvalidDate(){
        //Arrange
        String date= "02-22-2020";

        //Act

        //Assert
        Assertions.assertFalse(userService.validateDate(date));
    }

    @Test
    @DisplayName("Start Subscription Method Should Return True Given Valid Date")
    public void startSubscription_ShouldReturnTrue_GivenValidDate(){
        //Arrange
        String startDate = "02-01-2018";
        List<Subscription> subscriptions = new ArrayList<>();
        TopUp topup= null;
        User user = new User("1","user","02-01-2018",subscriptions,topup,0);
        when(userRepositoryMock.getUser(anyString())).thenReturn(user);
        when(userRepositoryMock.save(any(User.class))).thenReturn(user);

        //Act&
        //Assert
        Assertions.assertTrue(userService.startSubscription(startDate));
        verify(userRepositoryMock,times(1)).getUser(anyString());
        verify(userRepositoryMock,times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Start Subscription Method Should Throw InvalidDateException Given Invalid Date")
    public void startSubscription_ShouldThrowInvalidDateException_GivenInvalidDate(){
        //Arrange
        String startDate = "02-31-2018";
        List<Subscription> subscriptions = new ArrayList<>();
        TopUp topup= null;
        User user = new User("1","user","02-31-2018",subscriptions,topup,0);
        when(userRepositoryMock.getUser(anyString())).thenReturn(user);

        //Act&
        //Assert
        Assertions.assertThrows(InvalidDateException.class, () ->userService.startSubscription(startDate));
        verify(userRepositoryMock,times(1)).getUser(anyString());
    }

    

    

    

    
    
}
