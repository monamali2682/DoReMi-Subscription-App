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
import com.geektrust.backend.Repositories.IUserRepository;
import com.geektrust.backend.dtos.PrintRenewalDTO;
import com.geektrust.backend.entities.Plan;
import com.geektrust.backend.entities.Subscription;
import com.geektrust.backend.entities.TopUp;
import com.geektrust.backend.entities.User;
import com.geektrust.backend.exceptions.SubscriptionsNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("PrintServiceTest")
@ExtendWith(MockitoExtension.class)
public class PrintServiceTest {

    @Mock
    IUserRepository userRepositoryMock;

    @Mock
    ISubscriptionService subscriptionServiceMock;

    @InjectMocks
    PrintService printService;


    @Test
    @DisplayName("Print renewal Dates Should Throw SubscriptionsNotFoundException if subscriptions Is Empty")
    public void printRenewalDates_ShouldThrowSubscriptionsNotFoundException_subscriptionsIsEmpty(){
        //Arrange
        String date = "11-01-2019";
        List<Subscription> subscriptions = new ArrayList<>();
        TopUp topup= null;
        User user = new User("1","user",date,subscriptions,topup,0);
        when(userRepositoryMock.getUser(anyString())).thenReturn(user);

        //Act&
        //Assert
        Assertions.assertThrows(SubscriptionsNotFoundException.class, () -> printService.printRenewalDates());
        verify(userRepositoryMock,times(1)).getUser(anyString());
    }

    @Test
    @DisplayName("Print renewal Dates Should Return Print Renewal DTO")
    public void printRenewalDates_ShouldReturnPrintRenewalDTO(){
        //Arrange
        String date = "11-01-2019";
        DateTimeFormatter formatter= DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate startDate = LocalDate.parse("11-01-2019",formatter);
        Plan plan= new Plan("1", "MUSIC", "PERSONAL", 100, 1, 1);
        Subscription subscription = new Subscription("1", "MUSIC", startDate,plan);
        List<Subscription> subscriptions = new ArrayList<>(Arrays.asList(subscription));
        TopUp topup= new TopUp("1", "FOUR_DEVICE", 4, 50);
        User user = new User("1","user",date,subscriptions,topup,2);
        PrintRenewalDTO expectRenewalDTO = new PrintRenewalDTO(List.of("RENEWAL_REMINDER MUSIC 01-02-2019"),200);
        when(userRepositoryMock.getUser(anyString())).thenReturn(user);
        when(subscriptionServiceMock.getRenewalInfo(any(Subscription.class))).thenReturn("RENEWAL_REMINDER MUSIC 01-02-2019");

        //Act&
        PrintRenewalDTO actualRenewalDTO = printService.printRenewalDates();
        //Assert
        Assertions.assertEquals(expectRenewalDTO.toString(), actualRenewalDTO.toString());
        verify(userRepositoryMock,times(1)).getUser(anyString());
        verify(subscriptionServiceMock, times(1)).getRenewalInfo(subscription);
    }
}
