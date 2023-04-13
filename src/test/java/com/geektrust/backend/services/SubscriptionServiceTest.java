package com.geektrust.backend.services;


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
import com.geektrust.backend.entities.Plan;
import com.geektrust.backend.entities.Subscription;
import com.geektrust.backend.entities.TopUp;
import com.geektrust.backend.entities.User;
import com.geektrust.backend.exceptions.DuplicateCategogySubscriptionException;
import com.geektrust.backend.exceptions.InvalidDateException;
import com.geektrust.backend.Repositories.ISubscriptionRepository;
import com.geektrust.backend.Repositories.IUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("SubscriptionServiceTest")
@ExtendWith(MockitoExtension.class)
public class SubscriptionServiceTest{
    @Mock
    private ISubscriptionRepository subscriptionRepositoryMock;

    @Mock
    private IPlanService planServiceMock;

    @Mock
    IUserRepository userRepositoryMock;

    @InjectMocks
    private SubscriptionService subscriptionService;


    @Test
    @DisplayName("Get Reminder Date Should Return Reminder Date For Given Start Date And Validity")
    public void getReminderDate_ShouldReturnReminderDateGivenStartDateAndValidity(){
        //Arrange
        DateTimeFormatter formatter= DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate startDate = LocalDate.parse("11-01-2019",formatter);
        int validityInMonths = 1;
        String expectedReminderDate = "01-02-2019";

        //Act
        String actualReminderDate = subscriptionService.getReminderDate(startDate, validityInMonths);

        //Assert
        Assertions.assertEquals(expectedReminderDate,actualReminderDate);
    }

    @Test
    @DisplayName("Get Renewal Info Should Return Renewal Info For Given Subscription")
    public void getRenewalInfo_ShouldReturnRenewalInfoGivenSubscription(){
        //Arrange
        DateTimeFormatter formatter= DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate startDate = LocalDate.parse("11-01-2019",formatter);
        Plan plan= new Plan("1", "MUSIC", "FREE", 0, 1, 1);
        Subscription subscription = new Subscription("1", "MUSIC", startDate,plan);
        String expectedRenewalInfo = "RENEWAL_REMINDER MUSIC 01-02-2019";

        //Act
        String actualRenewalInfo = subscriptionService.getRenewalInfo(subscription);

        //Assert
        Assertions.assertEquals(expectedRenewalInfo,actualRenewalInfo);
    }

    @Test
    @DisplayName("Create Subscription Method Should Create Subscription")
    public void createSubscription_ShouldReturnSubscription(){
        //Arrange
        DateTimeFormatter formatter= DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate startDate = LocalDate.parse("11-01-2019",formatter);
        Plan plan= new Plan("1", "MUSIC", "FREE", 0, 1, 1);
        Subscription expectedSubscription = new Subscription("1", "MUSIC",startDate,plan);
        when(planServiceMock.getPlan(anyString(), anyString())).thenReturn(plan);
        when(subscriptionRepositoryMock.save(any(Subscription.class))).thenReturn(expectedSubscription);
        
        //Act
        Subscription actualSubscription = subscriptionService.createSubscription("MUSIC", "FREE");

        //Assert
        Assertions.assertEquals(expectedSubscription,actualSubscription);
        verify(subscriptionRepositoryMock,times(1)).save(any(Subscription.class));
    }

    @Test
    @DisplayName("Modify Start Date Should Change The Start Date Of Given Subscription")
    public void modifyStartDate_ShouldModifyStartDateOfGivenSubscription(){
        //Arrange
        DateTimeFormatter formatter= DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate startDate = LocalDate.parse("11-01-2019",formatter);
        Plan plan= new Plan("1", "MUSIC", "FREE", 0, 1, 1);
        Subscription subscription = new Subscription("1", "MUSIC",startDate,plan);
        LocalDate expectedStartDate = LocalDate.parse("12-07-2018",formatter);
        when(subscriptionRepositoryMock.save(any(Subscription.class))).thenReturn(subscription);
        //Act
        Subscription subscriptionModified = subscriptionService.modifyStartDate(subscription, expectedStartDate);

        //Assert
        Assertions.assertEquals(expectedStartDate, subscriptionModified.fetchStartDate());
    }

    @Test
    @DisplayName("Add Subscription Method Should Throw InvalidDateException if Start Date is null")
    public void addSubscription_ShouldThrowInvalidDateException_StartDateIsNull(){
        //Arrange
        String startDate = null;
        List<Subscription> subscriptions = new ArrayList<>();
        TopUp topup= null;
        User user = new User("1","user",startDate,subscriptions,topup,0);
        when(userRepositoryMock.getUser(anyString())).thenReturn(user);

        //Act&
        //Assert
        Assertions.assertThrows(InvalidDateException.class, () -> subscriptionService.addSubscription("MUSIC", "FREE"));
        verify(userRepositoryMock,times(1)).getUser(anyString());
    }

    @Test
    @DisplayName("Add Subscription Method Should Throw DuplicateCategogySubscriptionException if Subcription Already Present For Given Category")
    public void addSubscription_ShouldThrowDuplicateCategogySubscriptionException_GivenDuplicateCategory(){
        //Arrange
        String date = "11-01-2019";
        DateTimeFormatter formatter= DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate startDate = LocalDate.parse("11-01-2019",formatter);
        Plan plan= new Plan("1", "MUSIC", "FREE", 0, 1, 1);
        Subscription subscription = new Subscription("1", "MUSIC", startDate,plan);
        List<Subscription> subscriptions = new ArrayList<>(Arrays.asList(subscription));
        TopUp topup= null;
        User user = new User("1","user",date,subscriptions,topup,0);
        when(userRepositoryMock.getUser(anyString())).thenReturn(user);

        //Act&
        //Assert
        Assertions.assertThrows(DuplicateCategogySubscriptionException.class, () -> subscriptionService.addSubscription("MUSIC", "PREMIUM"));
        verify(userRepositoryMock,times(1)).getUser(anyString());
    }

    @Test
    @DisplayName("Add Subscription Method Should return True if Add Subscription Is Successfull")
    public void addSubscription_ShouldReturnTrue_AddSubscriptionSucceessfull(){
        //Arrange
        String date = "11-01-2019";
        List<Subscription> subscriptions = new ArrayList<>();
        TopUp topup= null;
        User user = new User("1","user",date,subscriptions,topup,0);
        // DateTimeFormatter formatter= DateTimeFormatter.ofPattern("dd-MM-yyyy");
        // LocalDate startDate = LocalDate.parse("11-01-2019",formatter);
        // Plan plan= new Plan("1", "MUSIC", "FREE", 0, 1, 1);
        //Subscription subscription = new Subscription("1", "MUSIC", startDate,plan);
        when(userRepositoryMock.getUser(anyString())).thenReturn(user);
        // when(subscriptionServiceMock.createSubscription(anyString(), anyString())).thenReturn(subscription);
        // when(subscriptionServiceMock.modifyStartDate(subscription, startDate)).thenReturn(subscription);
        when(userRepositoryMock.save(any(User.class))).thenReturn(user);

        //Act&
        //Assert
        Assertions.assertTrue(subscriptionService.addSubscription("MUSIC", "FREE"));
        verify(userRepositoryMock,times(1)).getUser(anyString());
        // verify(subscriptionServiceMock,times(1)).createSubscription(anyString(), anyString());
        // verify(subscriptionServiceMock,times(1)).modifyStartDate(any(Subscription.class), any(LocalDate.class));
        verify(userRepositoryMock,times(1)).save(any(User.class));
    }
}
