package com.geektrust.backend.commands;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import com.geektrust.backend.exceptions.DuplicateCategogySubscriptionException;
import com.geektrust.backend.exceptions.InvalidDateException;
import com.geektrust.backend.services.SubscriptionService;
import com.geektrust.backend.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@DisplayName("AddSubscriptionCommandTest")
@ExtendWith(MockitoExtension.class)
public class AddSubscriptionCommandTest{
    private final PrintStream standardOut= System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Mock
    SubscriptionService subscriptionServiceMock;

    @InjectMocks
    AddSubscriptionCommand addSubscriptionCommand;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }


    @Test
    @DisplayName("execute method of AddSubscriptionCommand Should Print Error Message To Console if Start Date is Invalid")
    public void execute_ShouldPrintErrorMessage_StartDateIsInvalid(){
        
        //Arrange
        String subscriptionCategory = "MUSIC";
        String planName = "PERSONAL";
        String expectedOutput = "ADD_SUBSCRIPTION_FAILED INVALID_DATE";
        doThrow(new InvalidDateException(expectedOutput)).when(subscriptionServiceMock).addSubscription(subscriptionCategory, planName);

        //Act
        addSubscriptionCommand.execute(List.of("ADD_SUBSCRIPTION",subscriptionCategory,planName));
         
        //Assert
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
        verify(subscriptionServiceMock,times(1)).addSubscription(subscriptionCategory, planName);
    }


    @Test
    @DisplayName("execute method of AddSubscriptionCommand Should Print Error Message To Console When Subscription Already Present For Category")
    public void execute_ShouldPrintErrorMessage_SubscriptionAlreadyPresentGivenCategory(){
        
        //Arrange
        String subscriptionCategory = "MUSIC";
        String planName = "PREMIUM";
        String expectedOutput = "ADD_SUBSCRIPTION_FAILED DUPLICATE_CATEGORY";
        doThrow(new DuplicateCategogySubscriptionException(expectedOutput)).when(subscriptionServiceMock).addSubscription(subscriptionCategory, planName);

        //Act
        addSubscriptionCommand.execute(List.of("ADD_SUBSCRIPTION",subscriptionCategory,planName));
         
        //Assert
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
        verify(subscriptionServiceMock,times(1)).addSubscription(subscriptionCategory, planName);
    }


    @Test
    @DisplayName("execute method of AddSubscriptionCommand Should Print Nothing When Add Subscription Is Successful")
    public void execute_ShouldPrintNothing_AddSubscriptionSuccessful(){
        
        //Arrange
        String subscriptionCategory = "MUSIC";
        String planName = "PREMIUM";
        String expectedOutput = "";
        when(subscriptionServiceMock.addSubscription(subscriptionCategory, planName)).thenReturn(true);

        //Act
        addSubscriptionCommand.execute(List.of("ADD_SUBSCRIPTION",subscriptionCategory,planName));
         
        //Assert
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
        verify(subscriptionServiceMock,times(1)).addSubscription(subscriptionCategory, planName);
    }

    

    @AfterEach
    public void tearDown(){
        System.setOut(standardOut);
    }
    
}
