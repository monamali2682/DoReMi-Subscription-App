package com.geektrust.backend.services;

import com.geektrust.backend.entities.Plan;
import com.geektrust.backend.entities.Subscription;
import com.geektrust.backend.entities.TopUp;
import com.geektrust.backend.entities.User;
import com.geektrust.backend.exceptions.DuplicateTopupFoundException;
import com.geektrust.backend.exceptions.SubscriptionsNotFoundException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import com.geektrust.backend.Repositories.ITopupRepository;
import com.geektrust.backend.Repositories.IUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("TopupServiceTest")
@ExtendWith(MockitoExtension.class)
public class TopupServiceTest{
    @Mock
    private ITopupRepository topupRepositoryMock;

    @Mock
    IUserRepository userRepositoryMock;

    @InjectMocks
    private TopupService topupService;

    @Test
    @DisplayName("Add Topup Method Should Create Topup")
    public void addTopup_ShouldReturnTopup(){
        //Arrange
        TopUp expectedTopup = new TopUp("FOUR_DEVICE",4,50);
        when(topupRepositoryMock.save(any(TopUp.class))).thenReturn(expectedTopup);
        //Act
        TopUp actualTopUp = topupService.addTopup("FOUR_DEVICE", 4,50);

        //Assert
        Assertions.assertEquals(expectedTopup,actualTopUp);
        verify(topupRepositoryMock,times(1)).save(any(TopUp.class));
    }

    @Test
    @DisplayName("Get Topup Method Should Return Topup Given Topup Name")
    public void getTopup_ShouldReturnTopupGivenTopupName(){
        //Arrange
        String topupName = "FOUR_DEVICE";
        TopUp expectedTopUp = new TopUp(topupName,4,50);
        when(topupRepositoryMock.findByname(anyString())).thenReturn(Optional.of(expectedTopUp));

        //Act
        TopUp actualTopUp = topupService.getTopUp(topupName);

        //Assert
        Assertions.assertEquals(expectedTopUp, actualTopUp);
        verify(topupRepositoryMock,times(1)).findByname(anyString());
    }

    @Test
    @DisplayName("Add Topup Method Should Throw DuplicateTopupFoundException if Topup Already Present")
    public void addTopup_ShouldThrowDuplicateTopupFoundException_GivenDuplicateTopup(){
        //Arrange
        String date = "11-01-2019";
        DateTimeFormatter formatter= DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate startDate = LocalDate.parse("11-01-2019",formatter);
        Plan plan= new Plan("1", "MUSIC", "FREE", 0, 1, 1);
        Subscription subscription = new Subscription("1", "MUSIC", startDate,plan);
        List<Subscription> subscriptions = new ArrayList<>(Arrays.asList(subscription));
        TopUp topup= new TopUp("1", "FOUR_DEVICE", 4, 50);
        User user = new User("1","user",date,subscriptions,topup,1);
        when(userRepositoryMock.getUser(anyString())).thenReturn(user);

        //Act&
        //Assert
        Assertions.assertThrows(DuplicateTopupFoundException.class, () -> topupService.addTopUP("FOUR_DEVICE", 2));
        verify(userRepositoryMock,times(1)).getUser(anyString());
    }

    @Test
    @DisplayName("Add Topup Method Should Throw SubscriptionsNotFoundException if subscriptions Is Empty")
    public void addTopup_ShouldThrowSubscriptionsNotFoundException_subscriptionsIsEmpty(){
        //Arrange
        String date = "11-01-2019";
        List<Subscription> subscriptions = new ArrayList<>();
        TopUp topup= null;
        User user = new User("1","user",date,subscriptions,topup,0);
        when(userRepositoryMock.getUser(anyString())).thenReturn(user);

        //Act
        TopUp actualTopup = topupService.addTopup("FOUR_DEVICE",4,50);
        //Assert
        Assertions.assertThrows(SubscriptionsNotFoundException.class, () -> topupService.addTopUP("FOUR_DEVICE", 2));
        verify(userRepositoryMock,times(1)).getUser(anyString());
    }

    // @Test
    // @DisplayName("Add Topup Method Should Return True if Add Topup Is Successful")
    // public void addTopup_ShouldReturnTrue_AddTopupSuccessful(){
    //     //Arrange
    //     String date = "11-01-2019";
    //     DateTimeFormatter formatter= DateTimeFormatter.ofPattern("dd-MM-yyyy");
    //     LocalDate startDate = LocalDate.parse("11-01-2019",formatter);
    //     Plan plan= new Plan("1", "MUSIC", "FREE", 0, 1, 1);
    //     Subscription subscription = new Subscription("1", "MUSIC", startDate,plan);
    //     List<Subscription> subscriptions = new ArrayList<>(Arrays.asList(subscription));
    //     TopUp topup= null;
    //     User user = new User("1","user",date,subscriptions,topup,0);
    //     //topup= new TopUp("1", "FOUR_DEVICE", 4, 50);
    //     // when(userRepositoryMock.getUser(anyString())).thenReturn(user);
    //     // when(userRepositoryMock.save(any(User.class))).thenReturn(user);
    //     // Optional<TopUp> topUp = Optional.of(topupService.addTopup("FOUR_DEVICE",4,50));
    //     // when(topupRepositoryMock.findByname(anyString())).thenReturn(topUp);
        
    //     //Act & Assert
    //     // Assertions.assertTrue(topupService.addTopUP("FOUR_DEVICE", 2));
    //     // verify(userRepositoryMock,times(1)).getUser(anyString());
    //     //verify(topupServiceMock,times(1)).getTopUp(anyString());
    //     // verify(userRepositoryMock,times(1)).save(any(User.class));
    // }
}
