package com.geektrust.backend.commands;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.geektrust.backend.dtos.PrintRenewalDTO;
import com.geektrust.backend.exceptions.SubscriptionsNotFoundException;
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


@DisplayName("PrintRenewalDatesCommandTest")
@ExtendWith(MockitoExtension.class)
public class PrintRenewalDatesCommandTest{
    private final PrintStream standardOut= System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Mock
    UserService userServiceMock;

    @InjectMocks
    PrintRenewalDatesCommand printRenewalDatesCommand;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }


    @Test
    @DisplayName("execute method of PrintRenewalDatesCommand Should Print Error Message To Console if Subscriptions Not Found")
    public void execute_ShouldPrintErrorMessage_SubscriptionsNotFound(){
        
        //Arrange
        String expectedOutput = "SUBSCRIPTIONS_NOT_FOUND";
        doThrow(new SubscriptionsNotFoundException(expectedOutput)).when(userServiceMock).printRenewalDates();

        //Act
        printRenewalDatesCommand.execute(List.of("PRINT_RENEWAL_DETAILS"));
         
        //Assert
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
        verify(userServiceMock,times(1)).printRenewalDates();
    }


    @Test
    @DisplayName("execute method of PrintRenewalDatesCommand Should Print Renewal DTO")
    public void execute_ShouldPrintErrorMessage_SubscriptionAlreadyPresentGivenCategory(){
        
        //Arrange
        String expectedOutput = "RENEWAL_REMINDER MUSIC 10-03-2022\n"+
        "RENEWAL_REMINDER VIDEO 10-05-2022\n" +
        "RENEWAL_REMINDER PODCAST 10-03-2022\n" +
        "RENEWAL_AMOUNT 750";

        List<String> renewalReminders= new ArrayList<String>(Arrays.asList("RENEWAL_REMINDER MUSIC 10-03-2022", "RENEWAL_REMINDER VIDEO 10-05-2022","RENEWAL_REMINDER PODCAST 10-03-2022")); 
        int renewalAmount = 750;
        when(userServiceMock.printRenewalDates()).thenReturn(new PrintRenewalDTO(renewalReminders, renewalAmount));

        //Act
        printRenewalDatesCommand.execute(List.of("PRINT_RENEWAL_DETAILS"));
         
        //Assert
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
        verify(userServiceMock,times(1)).printRenewalDates();
    }



    @AfterEach
    public void tearDown(){
        System.setOut(standardOut);
    }
    
}
