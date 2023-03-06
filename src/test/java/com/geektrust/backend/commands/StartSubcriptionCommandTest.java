package com.geektrust.backend.commands;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import com.geektrust.backend.exceptions.InvalidDateException;
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


@DisplayName("StartSubcriptionCommandTest")
@ExtendWith(MockitoExtension.class)
public class StartSubcriptionCommandTest{
    private final PrintStream standardOut= System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Mock
    UserService userServiceMock;

    @InjectMocks
    StartSubcriptionCommand startSubcriptionCommand;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }


    @Test
    @DisplayName("execute method of StartSubscriptionCommand Should Print Error Message To Console if Start Date is Invalid")
    public void execute_ShouldPrintErrorMessage_StartDateIsInvalid(){
        
        //Arrange
        String date = "02-34-2021";
        String expectedOutput = "INVALID_DATE";
        doThrow(new InvalidDateException(expectedOutput)).when(userServiceMock).startSubscription(date);

        //Act
        startSubcriptionCommand.execute(List.of("START_SUBSCRIPTION",date));
         
        //Assert
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
        verify(userServiceMock,times(1)).startSubscription(date);
    }


    @Test
    @DisplayName("execute method of StartSubscriptionCommand Should Print Nothing if Start Subscription is Successful")
    public void execute_ShouldPrintNothing_StartSubscriptionSuccessful(){
        
        //Arrange
        String date = "02-12-2021";
        String expectedOutput = "";
        when(userServiceMock.startSubscription(date)).thenReturn(true);

        //Act
        startSubcriptionCommand.execute(List.of("START_SUBSCRIPTION",date));
         
        //Assert
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
        verify(userServiceMock,times(1)).startSubscription(date);
    }


    @AfterEach
    public void tearDown(){
        System.setOut(standardOut);
    }
    
}
